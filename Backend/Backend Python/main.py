from flask import Flask
from flask_cors import CORS
from app.routes.gestionale_route import gestionale_bp
from app.routes.impiegato_route import impiegato_bp
from app.routes.laboratorio_route import laboratorio_bp
from app.routes.progetto_route import progetto_bp

app = Flask(__name__)

CORS(app)

app.register_blueprint(gestionale_bp)
app.register_blueprint(impiegato_bp)
app.register_blueprint(laboratorio_bp)
app.register_blueprint(progetto_bp)

@app.route("/")
def home():
    return {"message": "Benvenuto nel backend Python!"}

if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=5000)