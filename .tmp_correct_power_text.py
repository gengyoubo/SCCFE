import json
import zipfile
from pathlib import Path

zh_path = Path("src/main/resources/assets/sscfe/lang/zh_cn.json")
en_path = Path("src/main/resources/assets/sscfe/lang/en_us.json")
zh = json.loads(zh_path.read_text(encoding="utf-8"))
en = json.loads(en_path.read_text(encoding="utf-8"))
jar = zipfile.ZipFile("lib/shape_shifter_curse-1.9.3.14.jar")

specific = {
    "form_axolotl_3_sprinting_sneaking_water_explode": "站在方块上且处于冲刺状态时，按主动技能 1 向前冲撞，对附近生物造成伤害，并消耗 20 点氧气。冷却时间：3 秒。",
    "form_axolotl_3_slipperiness": "在非冰面、陆地上冲刺且氧气值大于 0 时，将脚下地面的滑行系数设为 0.35；不要求跳跃。",
    "form_axolotl_2_water_spurt": "水中水位达到 1 格时，冲刺状态从未开启变为开启会向前冲刺。冷却时间：5 秒。",
    "form_axolotl_3_sprinting_jump": "在陆地上、站在方块上且处于冲刺状态时起跳，会向前跃冲并消耗 3 点氧气。",
    "form_bat_3_jump_boost": "处于冲刺状态时起跳，会向前获得一次额外推进。",
    "form_ocelot_2_sneaking_jump_far": "在陆地站立且饥饿值至少为 6 时，潜行起跳会向前跃出一段距离。",
    "form_ocelot_3_jump_far": "在陆地站立且饥饿值至少为 6 时，潜行起跳会向前跃出一段距离。",
    "form_allay_sp_amethyst_shard_from_block": "将紫水晶块与紫水晶块组合，可消耗两个紫水晶块并获得 3 个紫水晶碎片。",
    "form_familiar_fox_2_craft_healing_potion": "消耗 5 点魔力，将普通药水与闪烁的西瓜片组合，获得 3 瓶治疗喷溅药水。",
    "form_familiar_fox_2_craft_harming_potion": "消耗 5 点魔力，将普通药水与发酵蛛眼组合，获得 3 瓶伤害喷溅药水。",
    "form_familiar_fox_2_craft_poison_potion": "消耗 5 点魔力，将普通药水与蜘蛛眼组合，获得 3 瓶中毒喷溅药水。",
    "form_familiar_fox_3_craft_healing_potion": "消耗 5 点魔力，将普通药水与闪烁的西瓜片组合，获得 3 瓶治疗喷溅药水。",
    "form_familiar_fox_3_craft_harming_potion": "消耗 5 点魔力，将普通药水与发酵蛛眼组合，获得 3 瓶伤害喷溅药水。",
    "form_familiar_fox_3_craft_poison_potion": "消耗 5 点魔力，将普通药水与蜘蛛眼组合，获得 3 瓶中毒喷溅药水。",
    "form_spider_3_make_cobweb": "消耗 5 点魔力，将两根线组合成一个蜘蛛网。",
    "form_spider_3_make_web_composter": "消耗 20 点魔力，将蜘蛛网与堆肥桶组合成蛛网堆肥桶。",
    "form_familiar_fox_3_attract_to_witch2": "女巫或唤魔者靠近 15 格内时会吸引你靠近；靠近至 2.5 格后停止。接近时你会获得反胃效果。",
    "form_familiar_fox_2_low_mana_debuff": "魔力低于最大值的 6% 时，获得失明与虚弱效果。",
    "form_tan_prevent_dirty_water_thirst": "需要安装 Tough As Nails。满足条件时，通过可选兼容钩子阻止污水口渴效果；Forge 端对此机制仅有部分兼容。",
    "form_allay_sp_sound": "随机叫声功能开启时，每 200 刻有 30% 几率播放悦灵的环境音。",
    "form_allay_sp_jukebox_heal": "距离 3 格内有正在播放唱片的唱片机时，每 40 刻恢复 2 点生命值。",
    "scare_skeleton": "骷髅不会主动选择你为攻击目标。",
    "form_anubis_wolf_sound": "随机叫声功能开启时，每 200 刻有 30% 几率播放狼的喘息声。",
    "form_axolotl_2_regeneration": "饥饿值至少为 6 且生命值低于 30 点时，每 400 刻恢复 1 点生命值。",
    "like_water": "在水中时限制下沉速度；潜行时不生效。",
    "form_axolotl_2_new_oxygen_regeneration": "饥饿值至少为 6、氧气值大于 0 且生命值低于 32 点时，每 200 刻恢复 1 点生命值。",
    "form_axolotl_sound": "随机叫声功能开启时，每 200 刻有 30% 几率播放美西螈的环境音。",
    "form_axolotl_3_sprinting_cost_oxygen": "在陆地上冲刺且氧气值大于 0 时，每 14 刻消耗 1 点氧气。",
    "form_axolotl_3_particle": "在陆地站立并冲刺时，每 5 刻生成水花与鹦鹉螺粒子。",
    "form_bat_3_sky_speed": "在空中冲刺、未使用鞘翅飞行时，持续获得向前推进。",
    "form_familiar_fox_2_attract_to_witch": "准星射线命中 20 格内的女巫或唤魔者时，向目标拉近并获得短暂反胃效果。",
    "form_familiar_fox_sound": "随机叫声功能开启时，每 200 刻有 30% 几率播放狐狸的环境音。",
    "form_familiar_fox_3_low_mana_damage": "魔力低于 6 点时，每 60 刻受到 1 点饥饿伤害。",
    "form_familiar_fox_3_in_fire_cost_mana": "处于火焰、灵魂火或熔岩中时，每 5 刻消耗 0.1 点魔力。",
    "scare_creepers": "苦力怕不会主动选择你为攻击目标。",
    "form_ocelot_sound": "随机叫声功能开启时，每 200 刻有 30% 几率播放豹猫的环境音。",
    "form_snow_fox_3_keep_burn": "处于燃烧状态时，每 20 刻刷新 4 秒燃烧时间。",
    "form_spider_3_mana_recover": "每 10 刻恢复 0.1 点魔力。",
    "form_spider_0_dew_covered_cobweb_particle": "距离至少 6 个带露蛛网方块 1 格内时，每 4 刻生成少量粒子。",
    "form_spider_0_instinct_max_particle": "本能值达到 95% 时，每 4 刻在周围生成提示粒子。",
    "form_spider_sound": "随机叫声功能开启时，每 200 刻有 30% 几率播放蜘蛛的环境音。",
    "form_spider_1_darkness": "每 40 刻获得持续 5 秒的黑暗效果。",
    "form_spider_1_particle": "持续生成附魔粒子效果。",
    "form_spider_1_instinct_max_particle": "本能值达到 95% 时，每 4 刻在周围生成提示粒子。",
    "can_loot_spider_fluid_cocoon": "击杀被蛛网完全缠住的生物时，有 40% 几率获得营养囊；部分生物不会掉落。",
    "spider_friendly": "蜘蛛不会主动选择你为攻击目标。",
    "hostile_iron_golem": "附近铁傀儡会主动将你设为目标；Forge 端的目标判定仍需游戏内验证。",
    "form_spider_3_auto_feed": "手持蜘蛛流体茧且饥饿值不高于 13 时，每 20 刻自动食用一个，恢复 7 点饥饿值和 0.8 饱和度。",
    "cat_friendly": "猫不会主动选择你为攻击目标。",
}

type_descriptions = {
    "apoli:exhaust": "该形态进行指定行动时会按配置增加饥饿消耗。",
    "shape-shifter-curse:bypass_landing_effect": "条件满足时免除此次着陆造成的坠落伤害。",
    "apoli:restrict_armor": "限制穿戴指定部位的护甲；形态专用装备可能不受限制。",
    "apoli:item_on_item": "可通过将指定物品与另一件指定物品组合来制作或转换物品。",
    "shape-shifter-curse:form_camera_bobbing": "改变该形态行走时的第一人称镜头晃动方式。",
    "shape-shifter-curse:delay_attribute": "在条件满足后延迟调整指定属性。",
    "apoli:entity_group": "将该形态归入指定生物类别，供其他能力和生物关系判定使用。",
    "shape-shifter-curse:modify-footstep-sound-speed": "调整该形态脚步声播放的速度。",
    "shape-shifter-curse:modify_footstep_sound_speed": "调整该形态脚步声播放的速度。",
    "shape-shifter-curse:keep_sneaking": "条件满足时强制保持潜行姿势。",
    "shape-shifter-curse:action_on_splash_potion_take_effect": "喷溅药水效果作用于你时触发配置的形态效果。",
    "shape-shifter-curse:action_on_jump": "满足条件时起跳会触发配置的动作。",
    "shape-shifter-curse:always_sprint_swimming": "游泳时持续保持冲刺游泳状态；饥饿消耗倍率按能力配置生效。",
    "shape-shifter-curse:bat_block_attach": "蝙蝠形态可按条件附着在方块侧面或底面。",
    "shape-shifter-curse:simple_looting": "提高战利品抢夺等级。",
    "shape-shifter-curse:in_water_speed_modifier": "在水中按能力配置的倍率调整移动速度。",
    "apoli:cooldown": "这是技能使用冷却计时器，不会单独产生额外效果。",
    "apoli:particle": "按配置条件和频率显示形态粒子效果。",
    "apoli:damage_over_time": "满足条件后按间隔持续受到伤害。",
    "shape-shifter-curse:burn_damage_modifier": "调整燃烧状态造成的伤害。",
    "apoli:prevent_game_event": "阻止指定的游戏事件发出。",
    "shape-shifter-curse:t_wolf_friendly": "咒文狼灵会按该能力的设定与此形态保持友好关系。",
    "shape-shifter-curse:tan_prevent_dirty_water_thirst_effect": "该兼容能力用于处理污水导致口渴的问题；Forge 端目前仅部分模拟这一机制。",
    "shape-shifter-curse:hold_breath": "在水中时持续补满氧气值，不会因呼吸耗尽氧气。",
    "shape-shifter-curse:mana_type_power": "为该形态配置魔力资源，供相关技能消耗；本条目本身不是可触发技能。",
    "shape-shifter-curse:witch_friendly": "女巫会按该能力的设定与此形态保持友好关系。",
    "shape-shifter-curse:prevent_berry_effect": "免受甜浆果丛造成的负面效果。",
    "shape-shifter-curse:pillager_friendly": "掠夺者会按该能力的设定与此形态保持友好关系。",
    "shape-shifter-curse:scare_villager": "村民会受到惊吓并逃离此形态。",
    "apoli:resource": "该条目储存相关技能的资源状态，供技能触发逻辑读取。",
    "shape-shifter-curse:apply_effect": "条件满足时施加配置的状态效果。",
    "shape-shifter-curse:attract_by_entity": "指定生物进入范围后触发吸引效果。",
    "shape-shifter-curse:hiss_phantom_power": "发出嘶声时触发配置的幻翼相关效果。",
    "shape-shifter-curse:projectile_dodge": "指定弹射物进入范围后尝试闪避，并按能力设置进入冷却。",
    "shape-shifter-curse:always_sweeping": "满足条件时，近战攻击会带有横扫效果。",
    "shape-shifter-curse:sneaking_jump_clash": "潜行起跳时对附近目标发动冲撞攻击；触发判定仍需游戏内验证。",
    "shape-shifter-curse:fox_friendly": "狐狸会按该能力的设定与此形态保持友好关系。",
    "shape-shifter-curse:critical_damage_modifier": "调整暴击攻击造成的伤害。",
    "shape-shifter-curse:enhanced_falling_attack": "增强从高处下落攻击时造成的伤害。",
    "shape-shifter-curse:snowball_block_transform": "雪球命中熔岩时会将其转化为黑曜石或石头。",
    "shape-shifter-curse:disable_player_rotation": "阻止该能力指定情形下的玩家旋转。",
    "shape-shifter-curse:hide_tp_held_item": "在第三人称视角隐藏手持物品。",
    "shape-shifter-curse:render_accessory_slot": "在形态模型上渲染配置的饰品栏物品。",
}

def description_for(power_id, data):
    if power_id in specific:
        return specific[power_id]
    kind = data.get("type", "")
    if kind == "shape-shifter-curse:delay_attribute":
        value = data.get("modifier", {}).get("value", 0)
        amount = abs(value)
        if power_id.endswith("_wither_health"):
            return f"受到凋零效果时，最大生命值提高 {amount:g} 点。"
        if power_id.endswith("_sun_health"):
            return f"暴露在阳光下时，最大生命值降低 {amount:g} 点。"
        if power_id.endswith("_desert_health"):
            return f"处于沙漠生物群系时，最大生命值降低 {amount:g} 点。"
        if power_id.endswith("_plain_health"):
            return f"处于平原生物群系时，最大生命值降低 {amount:g} 点。"
    if kind == "apoli:restrict_armor":
        slots = [label for field, label in (("head", "头部"), ("chest", "胸部"), ("legs", "腿部"), ("feet", "脚部")) if field in data]
        if power_id.endswith("_light_armor"):
            return "仅允许穿戴皮革或锁链胸甲、护腿和靴子；形态专用装备及豁免物品不受限制。"
        return "无法穿戴" + "、".join(slots) + "护甲；形态专用装备及豁免物品不受限制。"
    if kind == "apoli:entity_group":
        group = {"aquatic": "水生", "undead": "亡灵", "arthropod": "节肢动物"}.get(data.get("group"), "指定")
        return f"将该形态归类为{group}生物，供其他能力和生物关系判定使用。"
    if kind == "shape-shifter-curse:form_camera_bobbing":
        mode = {"float": "漂浮", "bat": "蝙蝠飞行", "feral": "野兽移动"}.get(data.get("bobbing_type"), "形态专属")
        return f"第一人称镜头采用{mode}时的晃动效果。"
    if kind == "apoli:cooldown":
        ticks = data.get("cooldown", 0)
        seconds = ticks / 20
        if "fire_arrow" in power_id:
            skill = "火焰箭"
        elif "fire_explode" in power_id:
            skill = "火焰爆发"
        elif "bottled_snowfall" in power_id:
            skill = "瓶装降雪"
        elif "bounce" in power_id:
            skill = "弹跳"
        elif "use_entity" in power_id:
            skill = "生物技能"
        else:
            skill = "对应技能"
        return f"记录{skill}的冷却时间（{seconds:g} 秒）；该计时器本身不产生额外效果。"
    if kind == "apoli:exhaust":
        interval = data.get("interval", 1)
        exhaustion = data.get("exhaustion", 0)
        return f"满足该能力条件时，每 {interval} 刻增加 {exhaustion:g} 点饥饿消耗。"
    if kind == "shape-shifter-curse:in_water_speed_modifier":
        modifier = data.get("modifier")
        if modifier is None:
            return "在水中降低移动速度。"
        return f"在水中按 {modifier:g} 倍调整移动速度。"
    if kind == "shape-shifter-curse:simple_looting":
        return f"战利品抢夺等级提高 {data.get('level', 1)} 级。"
    if kind == "shape-shifter-curse:modify_footstep_sound_speed":
        return f"脚步声播放速度提高至 {data.get('speed_multiplier', 1):g} 倍。"
    if kind == "shape-shifter-curse:always_sprint_swimming":
        return f"游泳时持续保持冲刺状态；饥饿消耗倍率为 {data.get('hunger_multiplier', 1):g}。"
    if kind == "apoli:resource":
        return f"储存 {data.get('min', 0)} 至 {data.get('max', 1)} 的技能资源状态，供相关技能读取。"
    if kind == "apoli:damage_over_time":
        interval = data.get("interval", 0) / 20
        damage = data.get("damage", 0)
        condition = "靠近熔岩" if "lava" in power_id else "靠近火源"
        return f"{condition}时每 {interval:g} 秒受到 {damage:g} 点火焰伤害。"
    return type_descriptions.get(kind, "")

fixed = 0
for key, old in list(zh.items()):
    if not (key.startswith("power.shape-shifter-curse.") and key.endswith(".description")):
        continue
    power_id = key[len("power.shape-shifter-curse."):-len(".description")]
    if old == "该能力会根据此形态的条件生效。":
        try:
            power = json.loads(jar.read(f"data/shape-shifter-curse/powers/{power_id}.json"))
        except KeyError:
            power = {}
        text = description_for(power_id, power)
        if text:
            zh[key] = text
            fixed += 1
for power_id, text in specific.items():
    key = f"power.shape-shifter-curse.{power_id}.description"
    if key in zh:
        zh[key] = text

for power_id in (
    "form_familiar_fox_2_tan_temperature_modify",
    "form_familiar_fox_3_tan_temperature_modify",
    "form_snow_fox_2_tan_temperature_remap",
    "form_snow_fox_3_tan_temperature_remap",
    "form_tan_hot_resistance_mild",
    "form_tan_cold_resistance_mild",
):
    key = f"power.shape-shifter-curse.{power_id}.description"
    path = f"data/shape-shifter-curse/powers/{power_id}.json"
    if key not in zh:
        continue
    power = json.loads(jar.read(path))
    hot = power.get("warm_offset", 0) < 0 or power.get("hot_offset", 0) < 0
    cold = power.get("icy_offset", 0) > 0 or power.get("cold_offset", 0) > 0
    direction = "降低温热环境下感受到的温度等级" if hot else "提高寒冷环境下感受到的温度等级" if cold else "调整不同温度环境下感受到的温度等级"
    zh[key] = f"需要安装 Tough As Nails；{direction}。未安装或兼容接口不可用时不生效。"

tan_thirst_key = "power.shape-shifter-curse.form_tan_axolotl_thirst_in_water.description"
if tan_thirst_key in zh:
    zh[tan_thirst_key] = "需要安装 Tough As Nails。完全浸没在水中时，每 10 刻尝试增加 1 点口渴值；Forge 端通过反射调用 TAN 接口，接口不匹配时此能力不生效。"

en_specific = {
    "form_axolotl_3_sprinting_sneaking_water_explode": "While standing on a block and sprinting, press Active Skill 1 to dash forward, damage nearby creatures, and consume 20 air. Cooldown: 3 seconds.",
    "form_axolotl_3_slipperiness": "While sprinting on land, outside water and off ice, with air remaining, sets the ground slipperiness modifier to 0.35. Jumping is not required.",
    "form_axolotl_2_water_spurt": "When the water height is at least one block, a transition from not sprinting to sprinting triggers a forward dash. Cooldown: 5 seconds.",
}
for power_id, text in en_specific.items():
    key = f"power.shape-shifter-curse.{power_id}.description"
    if key in en:
        en[key] = text

zh_path.write_text(json.dumps(zh, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
en_path.write_text(json.dumps(en, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
print(f"Replaced {fixed} generic descriptions; updated the three Axolotl trigger descriptions.")
