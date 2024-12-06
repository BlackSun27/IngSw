const API_URL = "/api/impiegati";

export const getImpiegati = async() =>{
    const response = await fetch('/api/gestionale/impiegati', {
        method : "GET",
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel prelievo di impiegati");
    }

    return response.json();
}

export const addImpiegato = async(impiegato) =>{
    const response = await fetch('$API_URL/', {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(impiegato),
    });
    if (!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nell'aggiunta dell'impiegato");
    }
    return response.json();
};

export const promuoviImpiegato = async(cf,merito) =>{
    const response = await fetch('$API_URL/$cf/promuovi/$merito', {
        method : "PATCH",
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nella promozione dell'impiegato");
    }

    return response.json();
};

export const getPromozioni = async(cf) =>{
    const response = await fetch('/api/gestionale/promozioni', {
        method : "GET",
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel prelievo delle promozioni");
    }

    return response.json();
};

export const rimuoviImpiegato = async(cf) =>{
    const response = await fetch('$API_URL/$cf', {
        method : "DELETE",
    });
    
    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nella rimozione dell'impiegato");
    }

    return response.json();
};

export const caricaAfferenze = async(cf) =>{
    const response = await fetch('$API_URL/$cf/laboratori',{
        method : "GET",        
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel recupero dei laboratori a cui lavora l'impiegato $cf.");
    }

    return response.json();
};

export const caricaProgetti = async(cf) =>{
    const response = await fetch('$API_URL/$cf/progetti',{
            method : "GET",
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel recupero del progetto a cui lavora $cf");
    }

    return response.json();
};