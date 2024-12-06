import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./src/Home";
import ImpiegatiList from "./Components/ImpiegatiList";
import LaboratoriList from "./Components/LaboratoriList";
import ProgettiList from "./Components/ProgettiList";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/impiegati" element={<ImpiegatiList />} />
        <Route path="/laboratori" element={<LaboratoriList />} />
        <Route path="/progetti" element={<ProgettiList />} />
      </Routes>
    </Router>
  );
};

export default App;