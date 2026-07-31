
## Pendências / To do (Bot System - Godot-like)

- [ ] `BotSystem` core
  - `catroid/src/main/java/org/catrobat/catroid/bot/BotSystem.java` (gerencia bots/AI instances, scheduling, message passing)
  - features:
    - spawn/instance bots (prefab scenes)
    - message passing / signals between bots and sprites
    - simple behavior trees / state machines per bot (extensible)
    - persistence of bot definitions in Project
- [ ] `BotDefinition` model
  - `catroid/src/main/java/org/catrobat/catroid/bot/BotDefinition.java` (serializável: name, scripts/behaviors, variables, initial position/layer)
- [ ] `BotEditor` UI
  - editor para criar/editar bots (attach scripts, set variables, choose prefab looks/animations)
- [ ] `BotBrick` (spawn/control)
  - `SpawnBotBrick` - instancia um BotDefinition em (x,y,layer)
  - `RemoveBotBrick` - remove instance
  - `SendBotMessageBrick` / `OnBotMessageBrick` - comunicação estilo Godot signals
- [ ] `BotBehavior` primitives (bricks)
  - `BotMoveToBrick`, `BotFollowBrick`, `BotWaitBrick`, `BotPlayAnimationBrick`, `BotStateMachineBrick`
- [ ] `BotAI` simple implementations
  - pathfinding hook (grid/simple), follow/seek behaviors, patrols
- [ ] Integration with Stage / Layers
  - bots are sprites/actors placed on layers and integrated into StageListener
- [ ] Example bots & demo scenes
  - enemy patrol, NPC that sends message on proximity, follower bot
- [ ] Docs / samples / tests

