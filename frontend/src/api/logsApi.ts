import apiClient from './client';
import type { DailyLog } from '../types';
import { format } from 'date-fns';

export async function getLogByDate(date: Date): Promise<DailyLog> {
    const formattedDate = format(date, 'yyyy-MM-dd');
    const response = await apiClient.get<DailyLog>(`/logs/${formattedDate}`);    
    return response.data;
}

export async function addMealToLog(date: Date, mealId: number): Promise<void> {
    const formattedDate = format(date, 'yyyy-MM-dd');
    await apiClient.post(`/logs/${formattedDate}/meals/${ mealId }`);
}

export async function addLiftingExerciseToLog(date: Date, exerciseId: number): Promise<void> {
    const formattedDate = format(date, 'yyyy-MM-dd');
    await apiClient.post(`/logs/${formattedDate}/lifting/${ exerciseId }`);
}

export async function addCardioExerciseToLog(date: Date, exerciseId: number): Promise<void> {
    const formattedDate = format(date, 'yyyy-MM-dd');
    await apiClient.post(`/logs/${formattedDate}/cardio/${ exerciseId }`);
}

export async function logWaterIntake(date: Date): Promise<DailyLog> {
    const formattedDate = format(date, 'yyyy-MM-dd');
    const response = await apiClient.patch<DailyLog>(`/logs/${formattedDate}/water/add`);
    return response.data;
}

export async function resetWaterIntake(date: Date): Promise<DailyLog> {
    const formattedDate = format(date, 'yyyy-MM-dd');
    const response = await apiClient.patch<DailyLog>(`/logs/${formattedDate}/water/reset`);
    return response.data;
}
