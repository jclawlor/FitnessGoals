import apiClient from './client';
import type { Meal, LiftingExercise, CardioExercise } from '../types';

export async function createMeal(data: Omit<Meal, 'id'>): Promise<Meal> {
    const response = await apiClient.post<Meal>('/meals', data);
    return response.data;
}

export async function createLiftingExercise(data:Omit<LiftingExercise, 'id'>): Promise<LiftingExercise> {
    const response = await apiClient.post<LiftingExercise>('/lifting', data);
    return response.data;
}

export async function createCardioExercise (data: Omit<CardioExercise, 'id'>): Promise<CardioExercise> {
    const response = await apiClient.post<CardioExercise>('/cardio', data);
    return response.data;
}

export async function getMeals(): Promise<Meal[]> {
    const response = await apiClient.get<Meal[]>('/meals');
    return response.data;
}

export async function getLiftingExercises(): Promise<LiftingExercise[]> {
    const response = await apiClient.get<LiftingExercise[]>('/lifting');
    return response.data;
}

export async function getCardioExercises(): Promise<CardioExercise[]> {
    const response = await apiClient.get<CardioExercise[]>('/cardio');
    return response.data;
}
