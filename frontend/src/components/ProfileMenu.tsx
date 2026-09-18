import { useState, useRef, useEffect } from 'react';
import { useClickOutside } from '../hooks/useClickOutside';
import { useAuth } from '../auth/useAuth';
import { useNavigate } from 'react-router-dom';
import { getCurrentUser } from '../api/profileApi';
import type { User } from '../types';
import '../App.css';

export default function ProfileMenu() {
  const [open, setOpen] = useState(false);
  const menuRef = useRef<HTMLDivElement>(null);
  const { logout } = useAuth();
  const navigate = useNavigate();
  const [user, setUser] = useState<User | null>(null);

  useClickOutside(menuRef, () => setOpen(false));

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  useEffect(() => {
    async function fetchCurrentUser() {
      const data = await getCurrentUser();
      setUser(data);

    }

    fetchCurrentUser();




  }, [])

  return (
  <div className="profile-menu" ref={menuRef}>

    <button
      className="profile-trigger"
      onClick={() => setOpen((prev) => !prev)}
    >
      <img
        src="/panda.png"
        alt="Profile"
        className="avatar"
      />

      {user && (
        <span className="profile-name">
          {user.username}
        </span>
      )}
    </button>

    {open && (
      <div className="dropdown">
        <button onClick={() => navigate('/profile')}>
          My Profile
        </button>

        <button onClick={() => navigate('/settings')}>
          Settings
        </button>

        <hr />

        <button onClick={handleLogout}>
          Log Out
        </button>
      </div>
    )}

  </div>
);
}