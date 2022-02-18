import { Routes, Route } from 'react-router-dom';
import TimeTracker from './pages/TimeTracker';
import BarraSuperior from './layout/BarraSuperior';
import InterfazInput from './layout/InterfazInput';

function App() {
  return (
    <div class="separadorPrincipal">
      <BarraSuperior/>
      <InterfazInput/>
      <Routes>
        <Route exact path="/" element={<TimeTracker/>} />
      </Routes>
    </div>
  );
}

export default App;
