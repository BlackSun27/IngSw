import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./components/Home";
import ImpiegatiPage from "./pages/ImpiegatiPage";
import ProgettiPage from "./pages/ProgettiPage";
import LaboratoriPage from "./pages/LaboratoriPage";
import NotFoundPage from "./pages/NotFoundPage";

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/impiegati" element={<ImpiegatiPage />} />
        <Route path="/progetti" element={<ProgettiPage />} />
        <Route path="/laboratori" element={<LaboratoriPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
      <Footer />
    </Router>
  );
}

export default App;