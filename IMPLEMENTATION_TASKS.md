# Implementação: Effects / Shaders / Particles / Animated Text (tracking)

Branch: feature/shader-layers-kix-dark-theme

Este arquivo é um checklist persistente para que eu (Copilot) não me esqueça das tarefas combinadas com você. Vou atualizar este arquivo à medida que for fazendo commits.

## Status (resumo rápido)

- [x] Adicionar strings para Effects e editor
  - `catroid/src/main/res/values/strings_effects.xml`
- [x] Layout da categoria Effects
  - `catroid/src/main/res/layout/brick_category_effects.xml`
- [x] Esqueleto de ParticleEffectAction
  - `catroid/src/main/java/org/catrobat/catroid/content/actions/ParticleEffectAction.java`
- [x] Modelo Layer
  - `catroid/src/main/java/org/catrobat/catroid/content/Layer.java`
- [x] Campo layerName em Sprite (inicial)
  - `catroid/src/main/java/org/catrobat/catroid/content/Sprite.java`


## Pendências / To do (alta prioridade)

- [ ] `ParticleProfile` model + persistência no `Project` (XStream)
  - criar `ParticleProfile.java`
  - adicionar `List<ParticleProfile>` ao `Project` e persistir
- [ ] Editor de ParticleProfile (UI)
  - lista de profiles (criar/editar/remover)
  - editor de profile com: nome, particleCount, duration, sizeScale, seleção múltipla de texturas com miniaturas (usando Looks do projeto)
  - salvar profile no `Project`
- [ ] `ParticleEffectBrick` (UI + modelo)
  - dropdown de profiles, overrides (count/duration/size), textureNamesCsv fallback, layer selector
- [ ] `ActionFactory.createParticleEffectAction(...)` e `ParticleEffectAction` final
  - interpretar fórmulas (Scope), resolver profile/CSV, carregar Textures (cache), criar `ParticleEffectActor`
- [ ] `ParticleEffectActor` (runtime)
  - aceitar 1+ textures, escolher aleatoriamente por partícula, física simples, auto-remove, dispose seguro
- [ ] Texture cache (refcount) + fallback 1x1 pixel

## Pendências / To do (shaders & layers)

- [ ] `ShaderManager` (compilação + cache + setUniforms + fallback ES2)
  - `catroid/src/main/java/org/catrobat/catroid/graphics/ShaderManager.java`
- [ ] `ShaderBrick` + runtime (associar shader a layer / set uniforms)
  - `catroid/src/main/java/org/catrobat/catroid/content/bricks/ShaderBrick.java`
- [ ] Render pipeline por layer (StageListener)
  - iterar `Scene.getLayers()` em z-order e desenhar sprites por layer, usar shader da layer quando presente
- [ ] Ajustes em `Look`/`LookData` para compatibilidade com uso de shader externo

## Pendências / To do (AnimatedText / Godot-like)

- [ ] `AnimatedTextActor` (com contorno, per-char effects, binding a UserVariable, tweens)
- [ ] `AnimatedTextBrick` runtime (criar actor e iniciar start/mid/end)
- [ ] `TextSetTextBrick`, `TextTweenBrick`, `TextTypewriterBrick`, `TextStyleBrick`, `TextRemoveBrick`
- [ ] UI do editor do AnimatedTextBrick (campo texto com inserção de variável, color pickers, presets)

## Integração / misc

- [ ] Inserir category view `brick_category_effects` em `BrickCategoryListBuilder` imediatamente após `Looks` (antes de `Pen/Data`)
- [ ] Projetos de exemplo e testes manuais (1–2 exemplos: particle effect + animated text + shader por layer)
- [ ] PR com commits atômicos, descrição e screenshots

## Notas / decisões confirmadas

- Nenhum `ParticleProfile` padrão será criado — perfil começa vazio.
- Layers padrão (criados automaticamente na migração): `background`, `controls`, `game`, `ui` — sprites antigos vão para `game`.
- Valores iniciais ao criar `ParticleProfile`: `particleCount=30`, `duration=2.0s`, `sizeScale=1.0`.
- Shaders limitados a GLSL ES2 (GLES20), suporte a `NORMAL` e `ADDITIVE` blend, tint básico; fallbacks seguros.
- Texto em partículas: texto estático (render-to-texture) no MVP; suporte dinâmico por variável a ser implementado depois ou junto com AnimatedText quando necessário.

---

Vou manter este arquivo atualizado. Se quiser que eu também crie uma Issue no GitHub com este checklist (para rastreamento), posso criar agora. Caso queira que os itens sejam subdivididos em issues individuais, diga e eu crio.
