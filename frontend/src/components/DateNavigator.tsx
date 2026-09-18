import { useState, useRef } from 'react';
import { DayPicker } from 'react-day-picker';
import {format, addDays, subDays } from 'date-fns';
import { ChevronLeft, ChevronRight, Calendar } from 'lucide-react';
import { useClickOutside } from '../hooks/useClickOutside';
import 'react-day-picker/dist/style.css';

interface DateNavigatorProps {
    selectedDate: Date;
    onDateChange: (date: Date) => void;
}

export default function DateNavigator({ selectedDate, onDateChange }: DateNavigatorProps) {
    const [showCalendar, setShowCalendar] = useState(false);
    const containerRef = useRef<HTMLDivElement>(null);

    useClickOutside(containerRef, () => setShowCalendar(false));

    const isToday = format(selectedDate, 'yyyy-MM-dd') === format(new Date(), 'yyyy-MM-dd');
    const label = isToday ? `Today, ${format(selectedDate, 'MMM d, yyyy')}` : format(selectedDate, 'EEE, MMM d, yyyy');

  return (
    <div className="date-navigator" ref={containerRef}>
      <button onClick={() => onDateChange(subDays(selectedDate, 1))} aria-label="Previous day">
        <ChevronLeft size={18} />
      </button>

      <button className="date-label" onClick={() => setShowCalendar((prev) => !prev)}>
        <Calendar size={16} />
        <span>{label}</span>
      </button>

      <button onClick={() => onDateChange(addDays(selectedDate, 1))} aria-label="Next day">
        <ChevronRight size={18} />
      </button>

      {showCalendar && (
        <div className="calendar-popover">
          <DayPicker
            mode="single"
            selected={selectedDate}
            onSelect={(date) => {
              if (date) {
                onDateChange(date);
                setShowCalendar(false);
              }
            }}
          />
        </div>
      )}
    </div>
  );
}

