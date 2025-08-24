package net.exmo.cataclysm_modifier;

import net.exmo.exmodifier.content.specialEffects.SpecialEffect;

import java.util.HashMap;
import java.util.Map;

public class SpecialEffects {
    public static Map<String , SpecialEffect> specialEffects = new HashMap<>();
    public final static SpecialEffect ZANGYAN = Builder.of("zangyan").build();
    public final static SpecialEffect DUNJI = Builder.of("dunji").build();
    public final static SpecialEffect HuoYu = Builder.of("huoyu").build();
    public final static SpecialEffect HUOZHONG = Builder.of("huozhong").build();
    public final static SpecialEffect YanYuZangSha = Builder.of("yanyuzangsha").build();
    public final static SpecialEffect YanDan = Builder.of("yandan").build();
    public final static SpecialEffect RongYanChongNeng = Builder.of("rongyanchongneng").build();
    public final static SpecialEffect HairenerShou = Builder.of("hairenershou").build();
    public final static SpecialEffect Zhanchuigaizhuang = Builder.of("zhanchuigaizhuang").build();
    public final static SpecialEffect FangYuGaizhuang = Builder.of("fangyugaizhuang").build();
    public final static SpecialEffect WajueGaizhuang = Builder.of("wajuegaizhuang").build();
    public final static SpecialEffect FangYuLingJia = Builder.of("fangyulingjia").build();
    public final static SpecialEffect ShenYuanZhiShui = Builder.of("shenyuanzhishui").build();
    public final static SpecialEffect ShenYuanQinShi = Builder.of("shenyuanqinshi").build();
    public final static SpecialEffect WeiDuChuanSuo = Builder.of("weiduchuansuo").build();
    public final static SpecialEffect XuKongZhongJi = Builder.of("xukongzhongji").build();
    public final static SpecialEffect XuKongNengYuan = Builder.of("xukongnengyuan").build();
    public final static SpecialEffect MoyingQianghua = Builder.of("moyingqianghua").build();
    public final static SpecialEffect XuKongQianghua = Builder.of("xukongqianghua").build();
    public final static SpecialEffect MoyingQinhe = Builder.of("moyingqinhe").build();
    public final static SpecialEffect HeJinZhuangJia = Builder.of("hejinzhuangjia").build();
    public final static SpecialEffect ZheShe = Builder.of("zheshe").build();
    public final static SpecialEffect DianCiXuanFu = Builder.of("diancixuanfu").build();
    public final static SpecialEffect JiGuangChuanTou = Builder.of("jiguangchuantou").build();

    public final static SpecialEffect ShazhiChan = Builder.of("shazhichen").build();
    public final static SpecialEffect ShabaoFeng = Builder.of("shabaofeng").build();
    public final static SpecialEffect YiHun = Builder.of("yihun").build();

    public final static SpecialEffect ZhouHun = Builder.of("zhouhun").build();
    public final static SpecialEffect ZhouHuan = Builder.of("zhouhuan").build();
    public final static SpecialEffect ShuangChui = Builder.of("shuangchui").build();
    public final static SpecialEffect ZhanJi = Builder.of("zhanji").build();




    public static class Builder{
        private String id;

        public static Builder of (String id) {
            return new Builder(id);
        }

        public Builder(String id) {
            this.id = id;
        }
        public SpecialEffect build(){
            SpecialEffect specialEffect = new SpecialEffect(id, null);
            specialEffects.put(id, specialEffect);
            return specialEffect;
        }
    }
}

