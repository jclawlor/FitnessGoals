export interface User {
  id: number;
  username: string;
  email: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface Meal {
    id: number;
    name: string;
    calories: number;
    carbohydrates: number;
    protein: number;
    fat: number;
    timeOfDay: 'BREAKFAST' | 'LUNCH' | 'DINNER' | 'SNACK';
    
}

export interface LiftingExercise {
  id: number;
  name: string;
  sets: number;
  reps: number;
  weight: number;
}

export type PaceUnit = 'PER_500M' | 'PER_MILE' | 'PER_KM' | 'PER_5K' | 'PER_10K' | 'PER_HALF_MARATHON' | 'PER_MARATHON';

export interface Pace {
  minutes: number,
  unit: PaceUnit;
}

export interface CardioExercise {
  id: number;
  name: string;
  durationMinutes: number;
  caloriesBurned: number;
  pace: Pace;
}

export interface DailyLog {
  username: string;
  date: string;
  meals: Meal[];
  liftingExercises: LiftingExercise[];
  cardioExercises: CardioExercise[];
  waterOunces: number;
}