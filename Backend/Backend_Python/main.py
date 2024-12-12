import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "..")))

from flask import Flask
from flask_cors import CORS
from Backend_Python.app.routes.gestionale_route import gestionale_bp
from Backend_Python.app.routes.impiegato_route import impiegato_bp
from Backend_Python.app.routes.laboratorio_route import laboratorio_bp
from Backend_Python.app.routes.progetto_route import progetto_bp
import logging
from logging.handlers import RotatingFileHandler

logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s - %(levelname)s - %(message)s',
    handlers=[
        logging.StreamHandler(),
        RotatingFileHandler('app.log', maxBytes=1000000, backupCount=3)
    ]
)

logger = logging.getLogger(__name__)

app = Flask(__name__)
CORS(app)

app.register_blueprint(gestionale_bp)
app.register_blueprint(impiegato_bp)
app.register_blueprint(laboratorio_bp)
app.register_blueprint(progetto_bp)

@app.route('/')
def home():
    logger.info("Endpoint '/' è stato chiamato.")
    return {"message": "Benvenuto nel backend Python!"}

if __name__ == '__main__':
    logger.info("Avvio dell'app Flask...")
    try:
        app.run(debug=True, host='0.0.0.0', port=5000)
    except Exception as e:
        logger.exception("Errore durante l'avvio dell'app Flask: %s", e)