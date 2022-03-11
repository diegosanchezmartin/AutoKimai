import { createContext, useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import TimeTracker from './pages/TimeTracker';
import BarraSuperior from './layout/BarraSuperior';
import InterfazInput from './layout/InterfazInput';

export const ActivityContext = createContext("Default activity");

export const ProjectContext = createContext("Default project");
/*export const ProjectContextWrapper = () => {
  const[projectChosen, setProjectChosen] = useState();


}*/

function App() {

  //createContext()
  const [projectChosen, setProjectChosen] = useState();
  const [activityChosen, setActivityChosen] = useState();

  //const projectValue = "Default Project";
  //const activityValue = "Default activity";

  return (
    <div class="separadorPrincipal">
        <ProjectContext.Provider value = {[projectChosen, setProjectChosen]}>
          <ActivityContext.Provider value = {[activityChosen, setActivityChosen]}>
            <BarraSuperior/>
            <InterfazInput/>
          </ActivityContext.Provider>
        </ProjectContext.Provider>
      <Routes>
        <Route exact path="/" element={<TimeTracker/>} />
      </Routes>
    </div>
  );
}

export default App;
