//components/AddMealLogModal.tsx
import { useState, useEffect } from 'react';
import Modal from './Modal';
import { getMeals } from '../api/activityApi';
import { addMealToLog } from '../api/logsApi';
import type { Meal } from '../types';

interface AddMealLogModalProps {
    selectedDate: Date;
    onClose: () => void;
    onSuccess: () => void;
}

export default function AddMealLogModal({ selectedDate, onClose, onSuccess }: AddMealLogModalProps) {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [meals, setMeals] = useState<Meal[]>([]);
    const [addingId, setAddingId] = useState<number | null>(null);


    useEffect(() => {
        let cancelled = false;

        async function fetchMeals() {
            setLoading(true);
            setError(null);

            try {
                const data = await getMeals();
                if (!cancelled) setMeals(data);
            } catch {
                if (!cancelled) setError('Failed to load meals.');
            } finally {
                if (!cancelled) setLoading(false);
            }
        }

        fetchMeals();

        return () => {
            cancelled = true;
        };
    }, []);

    const handleAdd = async (mealId: number) => {
        setAddingId(mealId);
        setError(null);
        try {
            await addMealToLog(selectedDate, mealId);
            onSuccess();
        } catch {
            setError('Failed to add meal to log.');
        } finally {
            setAddingId(null);
        }
    };

        return (
            <Modal onClose={onClose}>
                <h2> Add Meal to Log</h2>

                {loading && <p>Loading meals...</p>}
                {error && <p role="alert">{error}</p>}

                {!loading && !error && meals.length === 0 && <p>No meals in your library yet.</p>}

                {!loading && !error && meals.length > 0 && (
                    <ul className="log-select-list">
                        {meals.map((meal) => (
                            <li key={meal.id} className="log-select-row">
                                <div>
                                    <strong>{meal.name}</strong>
                                    <span> — {meal.calories} kcal</span>
                                </div>
                                <button
                                    onClick={() => handleAdd(meal.id)}
                                    disabled={addingId === meal.id}
                                    aria-label={`Add ${meal.name} to log`}
                                >
                                    {addingId === meal.id ? '...' : '+'}
                                </button>
                            </li>
                        ))}
                    </ul>
                )}

                <button type="button" onClick={onClose}>Close</button>
                    


        </Modal>
        );
    }