import { useState, type SubmitEvent } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../auth/useAuth';
import { login } from '../api/authApi';
import { User, Lock } from 'lucide-react';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const { login: loginContext } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e: SubmitEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      const data = await login({ username, password });
      loginContext(data.token);
      navigate('/dashboard');
    } catch {
      setError('Invalid username or password.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-card">
        <div className="auth-logo">
          <div className="auth-logo-icon">
            <img src="/logo-icon.png" alt="FitnessGoals" width={28} height={28} />
          </div>
          <span>FitnessGoals</span>
        </div>
        <h1>Log In</h1>

        
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Username</label>
          <div className="input-with-icon">
            <input
            id="username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            />
            <User size={18} className="input-icon" />
          </div>
          
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <div className="input-with-icon">
            <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            />
            <Lock size={18} className="input-icon"/>
          </div>
          
        </div>

        {error && <p role="alert">{error}</p>}

        <button type="submit" disabled={loading}>
          {loading ? 'Logging in...' : 'Log In'}
        </button>
      </form>

      <p>
        Don't have an account? <Link to="/register">Register here</Link>
      </p>
      </div>
      
    </div>
  );
}