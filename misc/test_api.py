import pytest
from httpx import AsyncClient
from app.main import app

@pytest.mark.asyncio
async def test_healthz():
    async with AsyncClient(app=app, base_url="http://test") as ac:
        r = await ac.get("/healthz")
    assert r.status_code == 200
    assert r.json()["status"] == "ok"

@pytest.mark.asyncio
async def test_2fat_verify():
    async with AsyncClient(app=app, base_url="http://test") as ac:
        r_ok = await ac.post("/auth/2fat/verify", json={"user_id":"u1","code":"123456"})
        r_bad = await ac.post("/auth/2fat/verify", json={"user_id":"u1","code":"000000"})
    assert r_ok.json()["status"] == "verified"
    assert r_bad.json()["status"] == "rejected"
