import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DealTraining from './pages/DealTraining';

const App: React.FC = () => {
  return (
      <Router basename="/app">  {/* basename 추가 */}
          <Routes>
              <Route path="/new" element={<DealTraining />} />
          </Routes>
      </Router>
  );
};

export default App;