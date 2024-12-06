const API_URL = "/api/progetto";

export const getProgetti = async () => {
    const response = await fetch("/api/gestionale/progetti", {
        method:"GET",
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel recuperare i progetti");
    }

    return response.json();
};

export const aggiungiProgetto = async (progetto) =>{
    const response = await fetch("$API_URL/", {
        method : "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(progetto),
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nell'aggiungere il progetto");
    }

    return response.json();
};

export const rimuoviProgetto = async(cup) => {
    const response = await fetch("$API_URL", {
        method : "DELETE",
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel rimuovere il progetto");
    }

    return response.json();
};

export const caricaImpiegati = async (cup) => { //esce prima il referente e poi il responsabile
    const response = await fetch("$API_URL/$cup/impiegati", {
        method: "GET",
    }); 

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error || "Errore nell'aggiungere il laboratorio al progetto");
    }

    return response.json();
};

export const caricaLaboratori = async (cup) => {
    const response = await fetch("/$API_URL/$cup",{
        method: "GET",
    });

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error || "Errore nell'aggiungere il laboratorio al progetto");
    }

    return response.json();
};