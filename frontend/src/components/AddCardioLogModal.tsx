import { useState, useEffect } from 'react';
import Modal from './Modal';
import { getCardioExercises } from '../api/activityApi';
import { addCardioExerciseToLog } from '../api/logsApi';
import type { CardioExercise } from '../types';

interface AddCardioLogModalProps {
    selectedDate: Date;
    onClose: () => void;
    onSuccess: () => void;
}

export default function AddCardioLogModal({ selectedDate, onClose, onSuccess }: AddCardioLogModalProps) {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [exercises, setExercises] = useState<CardioExercise[]>([]);
    const [addingId, setAddingId] = useState<number | null>(null);

    useEffect(() => {
        let cancelled = false;

        async function fetchExercises() {
            setLoading(true);
            setError(null);

            try {
                const data = await getCardioExercises();
                if (!cancelled) setExercises(data);
            } catch {
                if (!cancelled) setError('Failed to load cardio exercises.');
            } finally {
                if (!cancelled) setLoading(false);
            }
        }

        fetchExercises();

        return () => {
            cancelled = true;
        };

    }, []);

    function formatPace(minutes: number): string {
        const totalSeconds = Math.round(minutes * 60);
        const formattedMinutes = Math.floor(totalSeconds / 60);
        const formattedSeconds = totalSeconds % 60;

        return `${formattedMinutes}:${String(formattedSeconds).padStart(2, '0')}`;
    }

    const handleAdd = async (exerciseId: number) => {
        setAddingId(exerciseId);
        setError(null);
        try {
            await addCardioExerciseToLog(selectedDate, exerciseId);
            onSuccess();
        } catch {
            setError('Failed to add cardio exercise to log.');
        } finally {
            setAddingId(null);
        }
    };

    return (
        <Modal onClose={onClose}>
            <h2> Add Cardio Exercise to Log</h2>

            {loading && <p>Loading exercises...</p>}
            {error && <p role="alert">{error}</p>}

            {!loading && !error && exercises.length === 0 && <p>No exercises in your library yet.</p>}

            {!loading && !error && exercises.length > 0 && (
                <ul className="log-select-list">
                    {exercises.map((exercise) => (
                        <li key={exercise.id} className="log-select-row">
                            <div>
                                <strong>{exercise.name}</strong>
                                <span> — {exercise.durationMinutes} min</span>
                                <span> — {exercise.caloriesBurned} kcal</span>
                                <span> — {formatPace(exercise.pace.minutes)} minute(s) {exercise.pace.unit} </span>
                            </div>
                            <button
                                onClick={() => handleAdd(exercise.id)}
                                disabled={addingId === exercise.id}
                                aria-label={`Add ${exercise.name} to log`}
                            >
                                {addingId === exercise.id ? '...' : '+'}
                            </button>
                        </li>
                    ))}
                </ul>
            )}

            <button type="button" onClick={onClose}>Close</button>



        </Modal>
    );



}