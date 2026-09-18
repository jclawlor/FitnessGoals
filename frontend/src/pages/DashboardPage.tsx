import { useState, useEffect } from 'react';
import TopBar from '../components/TopBar';
import AddLogModal from '../components/AddLogModal';
import { getLogByDate, logWaterIntake, resetWaterIntake } from '../api/logsApi';
import type { DailyLog } from '../types';
import AddMealLogModal from '../components/AddMealLogModal';
import AddLiftingLogModal from '../components/AddLiftingLogModal';
import AddCardioLogModal from '../components/AddCardioLogModal';
import { RefreshCw } from 'lucide-react';

import {
    Utensils,
    Flame,
    Dumbbell,
    Footprints,
    Apple,
    Plus
} from 'lucide-react';

export default function DashboardPage() {
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [dailyLog, setDailyLog] = useState<DailyLog | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    type Tab = 'meals' | 'lifting' | 'cardio';
    const [activeTab, setActiveTab] = useState<Tab>('meals');
    const [showAddLogModal, setShowAddLogModal] = useState(false);
    type ActiveLogModal = 'meal' | 'lifting' | 'cardio' | null;
    const [activeLogModal, setActiveLogModal] = useState<ActiveLogModal>(null);

    // "BREAKFAST" -> "Breakfast"
    // function formatTimeOfDay(t: Meal['timeOfDay']): string {
    //     return t.charAt(0) + t.slice(1).toLowerCase();
    // }





    useEffect(() => {
        let cancelled = false;

        async function fetchDailyLog() {
            setLoading(true);
            setError(null);
            console.log(selectedDate);
            try {
                const data = await getLogByDate(selectedDate);
                if (!cancelled) setDailyLog(data);
            } catch {
                if (!cancelled) setError('Failed to load log for this date.');
            } finally {
                if (!cancelled) setLoading(false);
            }
        }

        fetchDailyLog();

        return () => {
            cancelled = true;
        };
    }, [selectedDate]);

    function formatPace(minutes: number): string {
        const totalSeconds = Math.round(minutes * 60);
        const formattedMinutes = Math.floor(totalSeconds / 60);
        const formattedSeconds = totalSeconds % 60;

        return `${formattedMinutes}:${String(formattedSeconds).padStart(2, '0')}`;
    }

    const handleLogCreated = () => {
        setShowAddLogModal(false);
        // No refetch needed yet — this just adds to the library,
        // doesn't attach to today's DailyLog.
    };


    const handleLogWaterIntake = async () => {
        try {
            await logWaterIntake(selectedDate);
            const updated = await getLogByDate(selectedDate);
            setDailyLog(updated);

        } catch {
            setError('Failed to log water.')
        }
    };

    const handleResetWaterIntake = async () => {
        try {
            await resetWaterIntake(selectedDate);
            const updated = await getLogByDate(selectedDate);
            setDailyLog(updated);
        } catch {
            setError('Failed to reset water.');
        }
    };


    const totalCalories = dailyLog?.meals?.reduce(
        (total, meal) => total + meal.calories,
        0
    ) ?? 0;

    const totalProtein = dailyLog?.meals?.reduce(
        (total, meal) => total + meal.protein,
        0
    ) ?? 0;

    const totalCarbs = dailyLog?.meals?.reduce(
        (total, meal) => total + meal.carbohydrates,
        0
    ) ?? 0;

    const totalFat = dailyLog?.meals?.reduce(
        (total, meal) => total + meal.fat,
        0
    ) ?? 0;

    const workoutCount =
        (dailyLog?.liftingExercises?.length ?? 0) +
        (dailyLog?.cardioExercises?.length ?? 0);

    return (
        <div className="dashboard">
            <TopBar selectedDate={selectedDate} onDateChange={setSelectedDate} />

            <div className="dashboard-content">

                <main className="dashboard-main">

                    {/* Summary cards */}
                    <section className="summary-cards">

                        <div className="summary-card">
                            <div className="summary-card-header">
                                <span>CALORIES CONSUMED</span>
                                <div className="summary-icon green">
                                    <Utensils size={20} />
                                </div>
                            </div>

                            <div className="summary-value">
                                {totalCalories}
                                <span> kcal</span>
                            </div>

                            <p>Goal: 2,400 kcal</p>
                        </div>


                        <div className="summary-card">
                            <div className="summary-card-header">
                                <span>CALORIES BURNED</span>
                                <div className="summary-icon blue">
                                    <Flame size={20} />
                                </div>
                            </div>

                            <div className="summary-value">
                                {dailyLog?.cardioExercises?.reduce(
                                    (total, exercise) =>
                                        total + exercise.caloriesBurned,
                                    0
                                ) ?? 0}
                                <span> kcal</span>
                            </div>

                            <p>From logged cardio</p>
                        </div>


                        <div className="summary-card">
                            <div className="summary-card-header">
                                <span>MACROS</span>
                                <span className="macro-percent">Daily total</span>
                            </div>

                            <div className="macro-row">
                                <span>Protein</span>
                                <strong>{totalProtein}g</strong>
                            </div>

                            <div className="macro-bar protein">
                                <span
                                    style={{
                                        width: `${Math.min((totalProtein / 180) * 100, 100)}%`
                                    }}
                                />
                            </div>

                            <div className="macro-row">
                                <span>Carbs</span>
                                <strong>{totalCarbs}g</strong>
                            </div>

                            <div className="macro-bar carbs">
                                <span
                                    style={{
                                        width: `${Math.min((totalCarbs / 250) * 100, 100)}%`
                                    }}
                                />
                            </div>

                            <div className="macro-row">
                                <span>Fat</span>
                                <strong>{totalFat}g</strong>
                            </div>

                            <div className="macro-bar fat">
                                <span
                                    style={{
                                        width: `${Math.min((totalFat / 80) * 100, 100)}%`
                                    }}
                                />
                            </div>
                        </div>


                        <div className="summary-card">
                            <div className="summary-card-header">
                                <span>WORKOUTS TRACKED</span>
                                <div className="summary-icon green">
                                    <Dumbbell size={20} />
                                </div>
                            </div>

                            <div className="summary-value">
                                {workoutCount}
                                <span> logged</span>
                            </div>

                            <p>
                                {dailyLog?.liftingExercises?.length ?? 0} lifting ·{' '}
                                {dailyLog?.cardioExercises?.length ?? 0} cardio
                            </p>
                        </div>

                    </section>


                    {/* Activity tabs */}
                    <nav className="activity-tabs">

                        <button
                            className={activeTab === 'meals' ? 'active' : ''}
                            onClick={() => setActiveTab('meals')}
                        >
                            Meals
                        </button>

                        <button
                            className={activeTab === 'lifting' ? 'active' : ''}
                            onClick={() => setActiveTab('lifting')}
                        >
                            Lifting Exercises
                        </button>

                        <button
                            className={activeTab === 'cardio' ? 'active' : ''}
                            onClick={() => setActiveTab('cardio')}
                        >
                            Cardio Exercises
                        </button>

                    </nav>


                    {/* Loading/error states */}
                    {loading && (
                        <div className="status-message">
                            Loading logs...
                        </div>
                    )}

                    {error && (
                        <div className="status-message error">
                            {error}
                        </div>
                    )}


                    {/* MEALS */}
                    {!loading && !error && dailyLog && activeTab === 'meals' && (
                        <section className="activity-section">

                            <div className="section-heading">
                                <div>
                                    <h2>Meals</h2>
                                    <p>Your meals for today</p>
                                </div>
                            </div>

                            {dailyLog.meals?.length === 0 && (
                                <div className="empty-state">
                                    <Apple size={32} />
                                    <p>No meals logged yet.</p>
                                </div>
                            )}

                            {dailyLog.meals?.map((meal) => (
                                <div key={meal.id} className="activity-card">

                                    <div className="activity-icon meal">
                                        <Apple size={22} />
                                    </div>

                                    <div className="activity-info">

                                        <div className="activity-meta">
                                            <span>
                                                {meal.timeOfDay}
                                            </span>
                                        </div>

                                        <h3>{meal.name}</h3>

                                        <div className="macro-tags">
                                            <span className="protein-tag">
                                                P: {meal.protein}g
                                            </span>

                                            <span className="carbs-tag">
                                                C: {meal.carbohydrates}g
                                            </span>

                                            <span className="fat-tag">
                                                F: {meal.fat}g
                                            </span>
                                        </div>

                                    </div>

                                    <div className="activity-calories">
                                        <strong>{meal.calories}</strong>
                                        <span>kcal</span>
                                    </div>

                                </div>
                            ))}

                            <button
                                className="secondary-add-button"
                                onClick={() => setActiveLogModal('meal')}
                            >
                                <Plus size={18} />
                                Add Meal
                            </button>

                        </section>
                    )}


                    {/* LIFTING */}
                    {!loading && !error && dailyLog && activeTab === 'lifting' && (
                        <section className="activity-section">

                            <div className="section-heading">
                                <div>
                                    <h2>Lifting Exercises</h2>
                                    <p>Your strength training for today</p>
                                </div>
                            </div>

                            {dailyLog.liftingExercises?.length === 0 && (
                                <div className="empty-state">
                                    <Dumbbell size={32} />
                                    <p>No lifting exercises logged yet.</p>
                                </div>
                            )}

                            {dailyLog.liftingExercises?.map((exercise) => (
                                <div key={exercise.id} className="activity-card">

                                    <div className="activity-icon lifting">
                                        <Dumbbell size={22} />
                                    </div>

                                    <div className="activity-info">
                                        <h3>{exercise.name}</h3>

                                        <p className="exercise-details">
                                            {exercise.sets} sets × {exercise.reps} reps
                                        </p>
                                    </div>

                                    <div className="activity-value">
                                        {exercise.weight} lb
                                    </div>

                                </div>
                            ))}

                            <button
                                className="secondary-add-button"
                                onClick={() => setActiveLogModal('lifting')}
                            >
                                <Plus size={18} />
                                Add Lifting Exercise
                            </button>

                        </section>
                    )}


                    {/* CARDIO */}
                    {!loading && !error && dailyLog && activeTab === 'cardio' && (
                        <section className="activity-section">

                            <div className="section-heading">
                                <div>
                                    <h2>Cardio Exercises</h2>
                                    <p>Your cardio training for today</p>
                                </div>
                            </div>

                            {dailyLog.cardioExercises?.length === 0 && (
                                <div className="empty-state">
                                    <Footprints size={32} />
                                    <p>No cardio exercises logged yet.</p>
                                </div>
                            )}

                            {dailyLog.cardioExercises?.map((exercise) => (
                                <div key={exercise.id} className="activity-card">

                                    <div className="activity-icon cardio">
                                        <Footprints size={22} />
                                    </div>

                                    <div className="activity-info">
                                        <h3>{exercise.name}</h3>

                                        <p className="exercise-details">
                                            {exercise.durationMinutes} min · {formatPace(exercise.pace.minutes)} min {exercise.pace.unit}
                                        </p>
                                    </div>

                                    <div className="activity-value">
                                        {exercise.caloriesBurned} kcal
                                    </div>

                                </div>
                            ))}

                            <button
                                className="secondary-add-button"
                                onClick={() => setActiveLogModal('cardio')}
                            >
                                <Plus size={18} />
                                Add Cardio Exercise
                            </button>

                        </section>
                    )}

                </main>


                {/* SIDEBAR */}
                <aside className="dashboard-sidebar">
                    <section className="sidebar-card">
                        <div className="sidebar-card-header">
                            <div>
                                <h2>Hydration</h2>
                                <p>Daily goal: 100 oz</p>
                            </div>

                            <span className="water-icon">💧</span>
                        </div>

                        <div className="hydration-value">
                            {dailyLog?.waterOunces || 0} <span>oz logged</span>
                        </div>

                        <div className="progress-track">
                            <div className="progress-fill" />
                        </div>

                        <button type="button" className="sidebar-action" onClick={() => handleLogWaterIntake()}>
                            + Log 16oz Water
                        </button>
                        <button type="button" className="reset-button" onClick={() => handleResetWaterIntake()}>
                            <RefreshCw size={16} />
                            <span>Reset</span>
                        </button>
                    </section>


                    <section className="sidebar-card quick-add">
                        <h2>Track New Activity</h2>

                        <p>
                            Add a meal or exercise to your daily log.
                        </p>

                        <button
                            className="sidebar-action"
                            onClick={() => setShowAddLogModal(true)}
                        >
                            + Log New Activity or Meal
                        </button>
                    </section>

                </aside>

            </div>

            {showAddLogModal && (
                <AddLogModal onClose={() => setShowAddLogModal(false)} onSuccess={handleLogCreated}
                />
            )}

            {activeLogModal === 'meal' && (
                <AddMealLogModal
                    selectedDate={selectedDate}
                    onClose={() => setActiveLogModal(null)}
                    onSuccess={() => {
                        setActiveLogModal(null);
                        getLogByDate(selectedDate).then(setDailyLog).catch(() => setError('Failed to reload log.'));
                    }}
                />
            )}

            {activeLogModal === 'lifting' && (
                <AddLiftingLogModal
                    selectedDate={selectedDate}
                    onClose={() => setActiveLogModal(null)}
                    onSuccess={() => {
                        setActiveLogModal(null);
                        getLogByDate(selectedDate).then(setDailyLog).catch(() => setError('Failed to reload log.'));
                    }}
                />
            )}

            {activeLogModal === 'cardio' && (
                <AddCardioLogModal
                    selectedDate={selectedDate}
                    onClose={() => setActiveLogModal(null)}
                    onSuccess={() => {
                        setActiveLogModal(null);
                        getLogByDate(selectedDate).then(setDailyLog).catch(() => setError('Failed to reload log.'));
                    }}
                />
            )}
        </div>
    );
}