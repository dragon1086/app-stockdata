import React from 'react';
import { User } from '../types/dealTraining';

interface NavbarProps {
  user: User | null;
  onLogin: () => void;
  onLogout: () => void;
  onOpenHistory: () => void;
}

const Navbar: React.FC<NavbarProps> = ({ user, onLogin, onLogout, onOpenHistory }) => {
  return (
    <nav className="bg-gray-800 text-white py-4">
      <div className="container mx-auto flex justify-between items-center px-4">
        <div className="text-xl font-bold">Stock Data App</div>
        <div className="flex items-center space-x-4">
          {user ? (
            <>
              <span>Welcome, {user.name}!</span>
              <button
                onClick={onOpenHistory}
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition-colors"
              >
                History
              </button>
              <button
                onClick={onLogout}
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition-colors"
              >
                Logout
              </button>
            </>
          ) : (
            <button
              onClick={onLogin}
              className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition-colors"
            >
              Login
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;