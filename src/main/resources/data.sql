INSERT INTO usuarios (id, nome, email, perfil) VALUES (1, 'Usuário Teste Comum', 'usuario.comum@fortepix.test', 'CLIENTE_COMUM');
INSERT INTO usuarios (id, nome, email, perfil) VALUES (2, 'Usuário Segurança Alta', 'seg.alta@fortepix.test', 'CLIENTE_SEGURANCA_ALTA');

INSERT INTO contas (id, usuario_id, chave_pix, saldo) VALUES (1, 1, 'chave-comum@fortepix.test', 1500.00);
INSERT INTO contas (id, usuario_id, chave_pix, saldo) VALUES (2, 2, 'chave-seg-alta@fortepix.test', 5000.00);

-- Blacklist antifraude de exemplo
INSERT INTO antifraude_blacklist (id, tipo, valor, motivo, ativo, data_criacao)
VALUES (1, 'CHAVE_PIX', 'chave-suspeita@fraude.test', 'Chave identificada em golpes anteriores', TRUE, CURRENT_TIMESTAMP);
