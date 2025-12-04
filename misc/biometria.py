from typing import Set


class BiometriaService:
    def __init__(self):
        self._hashes_validos: Set[str] = set()

    def registrar_biometria(self, hash_biometrico: str) -> None:
        if hash_biometrico:
            self._hashes_validos.add(hash_biometrico)

    def validar_biometria(self, hash_biometrico: str) -> bool:
        return bool(hash_biometrico) and hash_biometrico in self._hashes_validos
