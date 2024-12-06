import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h1>Gestione Aziendale</h1>
      <p>Benvenuto nel sistema gestionale. Scegli una delle seguenti sezioni:</p>
      <div style={{ display: "flex", justifyContent: "center", gap: "20px" }}>
        <Link to="/impiegati" style={{ textDecoration: "none", fontSize: "18px", color: "blue" }}>
          Gestione Impiegati
        </Link>
        <Link to="/laboratori" style={{ textDecoration: "none", fontSize: "18px", color: "blue" }}>
          Gestione Laboratori
        </Link>
        <Link to="/progetti" style={{ textDecoration: "none", fontSize: "18px", color: "blue" }}>
          Gestione Progetti
        </Link>
      </div>
    </div>
  );
};

export default Home;