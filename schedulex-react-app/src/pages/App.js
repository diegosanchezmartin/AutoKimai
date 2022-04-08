import { createContext, useState, useContext, useEffect } from "react";
import BarraSuperior from "../layout/BarraSuperior";
import InterfazInput from "../layout/InterfazInput";
import { CredentialContext } from "../Windows";
import { useNavigate } from "react-router-dom";

export const ActivityContext = createContext("Default activity");
export const ProjectContext = createContext("Default project");
export const ModalOpenContext = createContext("Defauhlt modal closed");

const App = () => {
  const [projectChosen, setProjectChosen] = useState();
  const [activityChosen, setActivityChosen] = useState();
  const [modalOpen, setModalOpen] = useState(false);
  const [credentials, setCrendentials] = useContext(CredentialContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (credentials.username === undefined && credentials.token === undefined) {
      navigate("/");
    }
  })

  if (credentials.username !== undefined && credentials.token !== undefined) {
    return (
      <div>
        <ProjectContext.Provider value={[projectChosen, setProjectChosen]}>
          <ActivityContext.Provider value={[activityChosen, setActivityChosen]}>
            <ModalOpenContext.Provider value={[modalOpen, setModalOpen]}>
              <BarraSuperior />
              <InterfazInput />
            </ModalOpenContext.Provider>
          </ActivityContext.Provider>
        </ProjectContext.Provider>
      </div>
    );
  } else {
    return null;
  }
}

export default App;
