//components/AddLogModal.tsx
import { useState, type SubmitEvent } from 'react';
import Modal from './Modal';
import { createMeal, createLiftingExercise, createCardioExercise } from '../api/activityApi';
import type { PaceUnit } from '../types'
type LogType = 'meal' | 'lifting' | 'cardio' | null;

interface AddLogModalProps {
    onClose: () => void;
    onSuccess: () => void;
}

export default function AddLogModal({ onClose, onSuccess }: AddLogModalProps) {
    const [logType, setLogType] = useState<LogType>(null);
    const [submitting, setSubmitting] = useState(false);
    const [error, setError] = useState<string | null>(null);

    function parsePace(value: string): number {
        const match = value.trim().match(/^(\d+):([0-5]\d)$/);

        if (!match) {
            throw new Error('Enter pace as mm:ss, for example 2:08.');
        }

        const minutes = Number(match[1]);
        const seconds = Number(match[2]);

        return minutes + seconds / 60;
    }

    const handleMealSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = new FormData(e.currentTarget);
        setSubmitting(true);
        setError(null);
        try {
            await createMeal({
                name: form.get('name') as string,
                timeOfDay: form.get('timeOfDay') as 'BREAKFAST' | 'LUNCH' | 'DINNER' | 'SNACK',
                calories: Number(form.get('calories')),
                carbohydrates: Number(form.get('carbohydrates')),
                protein: Number(form.get('protein')),
                fat: Number(form.get('fat')),
            });
            onSuccess();
        } catch {
            setError('Failed to add meal.');
        } finally {
            setSubmitting(false);
        }

    }

    const handleLiftingSubmit = async (e: SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = new FormData(e.currentTarget)
        setSubmitting(true);
        setError(null);
        try {
            await createLiftingExercise({
                name: form.get('name') as string,
                sets: Number(form.get('sets')),
                reps: Number(form.get('reps')),
                weight: Number(form.get('weight'))
            });
            onSuccess();
        } catch {
            setError('Failed to add meal.');
        } finally {
            setSubmitting(false);
        }
    }

    const handleCardioSubmit = async (e: SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = new FormData(e.currentTarget);
        setSubmitting(true);
        setError(null);
        try {
            await createCardioExercise({
                name: form.get('name') as string,
                durationMinutes: Number(form.get('durationMinutes')),
                caloriesBurned: Number(form.get('caloriesBurned')),
                pace: {
                    minutes: parsePace(form.get('paceMinutes') as string),
                    unit: form.get('paceUnit') as PaceUnit,
                },
            });
            onSuccess();
        } catch {
            setError('Failed to add cardio exercise.');
        } finally {
            setSubmitting(false);
        }
    };


    return (
        <Modal onClose={onClose}>
            {logType === null && (
                <div className="log-type-select">
                    <h2>What would you like to Log?</h2>
                    <button onClick={() => setLogType('meal')}>Meal</button>
                    <button onClick={() => setLogType('lifting')}>LiftingExercise</button>
                    <button onClick={() => setLogType('cardio')}>Cardio Exercise</button>
                </div>
            )}

            {logType === 'meal' && (
                <form onSubmit={handleMealSubmit}>
                    <h2>Add Meal</h2>
                    <label>
                        <span>Name</span>
                        <input name="name" type="text" required />
                    </label>
                    <label>
                        Time of Day
                        <select name="timeOfDay" required>
                            <option value="BREAKFAST">Breakfast</option>
                            <option value="LUNCH">Lunch</option>
                            <option value="DINNER">Dinner</option>
                            <option value="SNACK">Snack</option>
                        </select>
                    </label>
                    <label>
                        Calories
                        <input name="calories" type="number" required />
                    </label>
                    <label>
                        Protein (g)
                        <input name="protein" type="number" required />
                    </label>
                    <label>
                        Carbs (g)
                        <input name="carbohydrates" type="number" required />
                    </label>
                    <label>
                        Fat (g)
                        <input name="fat" type="number" required />
                    </label>

                    {error && <p role="alert">{error}</p>}

                    <div className="modal=actions">
                        <button type="button" onClick={() => setLogType(null)}>Back</button>
                        <button type="submit" disabled={submitting}>
                            {submitting ? 'Saving...' : 'Submit'}
                        </button>
                    </div>
                </form>
            )}
            {logType === 'lifting' && (
                <form onSubmit={handleLiftingSubmit}>
                    <h2>Add Lifting Exercise</h2>
                    <label>
                        Name
                        <input name="name" type="text" required />
                    </label>
                    <label>
                        Sets
                        <input name="sets" type="number" required />
                    </label>
                    <label>
                        Reps
                        <input name="reps" type="number" required />
                    </label>
                    <label>
                        Weight (lb)
                        <input name="weight" type="number" required />
                    </label>

                    {error && <p role="alert">{error}</p>}

                    <div className="modal-actions">
                        <button type="button" onClick={() => setLogType(null)}>Back</button>
                        <button type="submit" disabled={submitting}>
                            {submitting ? 'Saving...' : 'Submit'}
                        </button>
                    </div>
                </form>
            )}

            {logType === 'cardio' && (
                <form onSubmit={handleCardioSubmit}>
                    <h2>Add Cardio Exercise</h2>
                    <label>
                        Name
                        <input name="name" type="text" required />
                    </label>
                    <label>
                        Duration (min)
                        <input name="durationMinutes" type="number" required />
                    </label>
                    <label>
                        Calories Burned
                        <input name="caloriesBurned" type="number" required />
                    </label>
                    <label>
                        Pace (mm:ss)
                        <input name="paceMinutes" type="text" placeholder="6:30" pattern="\d+:[0-5]\d" required />
                    </label>
                    <label>
                        Pace Unit
                        <select name="paceUnit" required>
                            <option value="PER_500M">Per 500 m</option>
                            <option value="PER_MILE">Per mile</option>
                            <option value="PER_KM">Per kilometer</option>
                            <option value="PER_5K">Per 5K</option>
                            <option value="PER_10K">Per 10K</option>
                            <option value="PER_HALF_MARATHON">Per half marathon</option>
                            <option value="PER_MARATHON">Per marathon</option>
                        </select>
                    </label>

                    {error && <p role="alert">{error}</p>}

                    <div className="modal-actions">
                        <button type="button" onClick={() => setLogType(null)}>Back</button>
                        <button type="submit" disabled={submitting}>
                            {submitting ? 'Saving...' : 'Submit'}
                        </button>
                    </div>
                </form>
            )}
        </Modal>
    )

}

