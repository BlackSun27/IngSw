import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

const ImpiegatoDetail = () => {
  const { cf } = useParams();
  const [impiegato, setImpiegato] = useState(null);

  useEffect(() => {
    fetch(`/api/gestionale/impiegati/${cf}`)
      .then((res) => res.json())
      .then((data) => setImpiegato(data))
      .catch((err) => console.error("Errore:", err));
  }, [cf]);

  if (!impiegato) return <p>Caricamento...</p>;

  return (
    <div>
      <h1>Dettagli Impiegato</h1>
      <p><strong>Nome:</strong> {impiegato.nome}</p>
      <p><strong>Cognome:</strong> {impiegato.cognome}</p>
      <p><strong>Categoria:</strong> {impiegato.categoria}</p>
      <p><strong>Salario:</strong> {impiegato.salario} €</p>
      <p><strong>Data di Assunzione:</strong> {impiegato.data_assunzione}</p>
      <Link to="/impiegati">Torna alla lista</Link>
    </div>
  );
};

export default ImpiegatoDetail;