import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DealTraining from './pages/DealTraining';

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/new-deal-training" element={<DealTraining />} />
      </Routes>
    </Router>
  );
};

export default App;