import apiClient from './client';
import type { User } from '../types';

export async function getCurrentUser(): Promise<User> {
    const response = await apiClient.get<User>(`/users/me`);    
    return response.data;
}