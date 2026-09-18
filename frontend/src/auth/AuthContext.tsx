import { createContext, useState, type ReactNode } from 'react';

interface AuthContextType {
    token: string | null;
    login: (token: string) => void;
    logout: () => void;
    isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);


export function AuthProvider({children}: {children: ReactNode}) {

    const [token, setToken] = useState<string | null>(
        localStorage.getItem('jwt')
    );

    const login = (newToken: string) => {
        localStorage.setItem('jwt', newToken);
        setToken(newToken);
    };

    const logout = () => {
        localStorage.removeItem('jwt');
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{token, login, logout, isAuthenticated: !!token }}>
            {children}
        </AuthContext.Provider>
    );
}

export default AuthContext;

