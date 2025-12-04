from fastapi import FastAPI
from pydantic import BaseModel
from typing import List, Optional

app = FastAPI(title="FortePix API (Python – Starter)")

class Send2FATRequest(BaseModel):
    user_id: str
    channel: str = "email"

class Verify2FATRequest(BaseModel):
    user_id: str
    code: str

class PixInitiateRequest(BaseModel):
    payer_id: str
    amount: float
    currency: str = "BRL"
    pix_key: str
    pix_key_type: str

class AntifraudRequest(BaseModel):
    payer_id: str
    amount: float
    pix_key: str
    pix_key_type: str
    device_id: Optional[str] = None
    ip_address: Optional[str] = None

class AntifraudResponse(BaseModel):
    score: float
    risk_level: str
    recommendation: str
    reasons: List[str]

@app.get("/healthz")
def healthz():
    return {"status": "ok", "service": "fortepix-python", "version": "0.1.0"}

@app.post("/auth/2fat/send")
def send_2fat(req: Send2FATRequest):
    return {"status": "sent", "user_id": req.user_id, "channel": req.channel}

@app.post("/auth/2fat/verify")
def verify_2fat(req: Verify2FATRequest):
    accepted = (req.code == "123456")
    return {"status": "verified" if accepted else "rejected", "user_id": req.user_id}

@app.post("/pix/initiate")
def pix_initiate(req: PixInitiateRequest):
    return {"status": "INITIATED","tx_id": "tx_demo_001","amount": req.amount,"currency": req.currency,"pix_key": req.pix_key,"pix_key_type": req.pix_key_type}

@app.post("/internal/antifraud/score", response_model=AntifraudResponse)
def antifraud_score(req: AntifraudRequest):
    score = 0.1
    reasons: List[str] = []
    key_lower = req.pix_key.lower()
    if "risco" in key_lower or "fraude" in key_lower:
        score = 0.9
        reasons.append("PIX_TO_FLAGGED_PATTERN")
    if score >= 0.85:
        return AntifraudResponse(score=score, risk_level="CRITICAL", recommendation="BLOCK", reasons=reasons)
    if score >= 0.6:
        return AntifraudResponse(score=score, risk_level="HIGH", recommendation="CHALLENGE", reasons=reasons)
    if score >= 0.3:
        return AntifraudResponse(score=score, risk_level="MEDIUM", recommendation="WARN", reasons=reasons)
    return AntifraudResponse(score=score, risk_level="LOW", recommendation="ALLOW", reasons=reasons)
