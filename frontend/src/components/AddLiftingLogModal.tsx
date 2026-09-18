import { useState, useEffect } from 'react';
import Modal from './Modal';
import { getLiftingExercises } from '../api/activityApi';
import { addLiftingExerciseToLog } from '../api/logsApi';
import type { LiftingExercise } from '../types';

interface AddLiftingLogModalProps {
    selectedDate: Date;
    onClose: () => void;
    onSuccess: () => void;
}

export default function AddLiftingLogModal({ selectedDate, onClose, onSuccess }: AddLiftingLogModalProps) {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [exercises, setExercises] = useState<LiftingExercise[]>([]);
    const [addingId, setAddingId] = useState<number | null>(null);


    useEffect(() => {
        let cancelled = false;

        async function fetchExercises() {
            setLoading(true);
            setError(null);

            try {
                const data = await getLiftingExercises();
                if (!cancelled) setExercises(data);
            } catch {
                if (!cancelled) setError('Failed to load lifting exercises.');
            } finally {
                if (!cancelled) setLoading(false);
            }
        }

        fetchExercises();

        return () => {
            cancelled = true;
        };
    }, []);


    const handleAdd = async (exerciseId: number) => {
        setAddingId(exerciseId);
        setError(null);

        try {
            await addLiftingExerciseToLog(selectedDate, exerciseId);
            onSuccess();
        } catch {
            setError('Failed to add lifting exercise to log.');

        } finally {
            setAddingId(null);
        }
    };

    return (
        <Modal onClose={onClose}>
            <h2>Add Lifting Exercise to Log</h2>

            {loading && <p>Loading exercises...</p>}
            {error && <p role='alert'>{error}</p>}

            {!loading && !error && exercises.length === 0 && <p>No lifting exercises in your library yet.</p>}

            {!loading && !error && exercises.length > 0 && (
                <ul className="log-select-list">
                    {exercises.map((exercise) => (
                        <li key={exercise.id} className="log-select-row">
                            <div>
                                <strong>{exercise.name}</strong>
                                <span> - {exercise.reps}</span>
                                <span> - {exercise.sets}</span>
                                <span> - {exercise.weight} lb</span>
                            </div>
                            <button onClick={() => handleAdd(exercise.id)}
                                disabled={addingId === exercise.id}
                                aria-label={`Add ${exercise.name} to log`}
                            >
                                {addingId === exercise.id ? '...' : '+'}
                            </button>
                        </li>
                    ))}
                </ul>
            )}
        </Modal>
    );
}




