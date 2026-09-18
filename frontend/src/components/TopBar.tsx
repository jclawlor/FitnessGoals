import DateNavigator from './DateNavigator';
import ProfileMenu from './ProfileMenu';

interface TopBarProps {
  selectedDate: Date;
  onDateChange: (date: Date) => void;
}

export default function TopBar({ selectedDate, onDateChange }: TopBarProps) {
  return (
    <header className="topbar">
      <div className="topbar__brand">
        <img src="/logo-icon.png" alt="FitnessGoals" width={28} height={28} />
        <span className="topbar__name">FitnessGoals</span>
      </div>
      <DateNavigator selectedDate={selectedDate} onDateChange={onDateChange} />
      <ProfileMenu/>
    </header>
  );
}

