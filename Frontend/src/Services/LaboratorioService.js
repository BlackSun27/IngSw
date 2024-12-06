const API_URL = "api/laboratori";

export const getLaboratori = async() =>{
    const response = await fetch('/api/gestionale/laboratori', {
        method : "GET",
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel prelievo dei laboratori");
    }

    return response.json();
}

export const aggiungiLaboratori = async () => {
    const response = await fetch('$API_URL/', {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(laboratorio),
    });
    if (!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nell'aggiunta del laboratorio");
    }
    return response.json();
};

export const rimuoviImpiegato = async(nome) =>{
    const response = await fetch('$API_URL/$nome', {
        method : "DELETE",
    });
    
    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nella rimozione del laboratorio");
    }

    return response.json();
};

export const caricaAfferenze = async(nome) =>{
    const response = await fetch('$API_URL/$nome',{
        method : "GET",        
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel recupero degli impiegati che lavorano al laboratorio $nome.");
    }

    return response.json();
};

export const caricaResponsabile = async(nome) =>{
    const response = await fetch('$API_URL/$nome/responsabile',{
        method : "GET",        
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel recupero del responsabile che lavora al laboratorio $nome.");
    }

    return response.json();
};

export const caricaProgetti = async(nome) =>{
    const response = await fetch('$API_URL/$nome/progetti',{
        method : "GET",        
    });

    if(!response.ok){
        const error = await response.json();
        throw new Error(error.error || "Errore nel recupero del progetto a cui lavora il laboratorio $nome.");
    }

    return response.json();
};