package com.provider_oidc_ldsc.plan;

import com.alibaba.fastjson.JSONObject;
import com.provider_oidc_ldsc.domain.DateUtils;
import com.provider_oidc_ldsc.domain.RegexUtil;
import com.provider_oidc_ldsc.service.dto.CloneUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 信息化管理部-方波
 * @site http://www.cr121.com/
 * @company 中铁十二局集团第一工程有限公司
 * @create 2021-07-21 20:14
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class NetPlanItem implements Cloneable{
    /**
     * 工作id
     */
    private Integer id;
    /**
     * 工作父id
     */
    private Integer rootid;
    /**
     * 工作层级
     */
    private Integer level;

    private JSONObject A;

    /**
     * 工作名称
     */
    private String work;

    /**
     * 紧前工作id列表[1;2;3]
     */
    private String frontids;
    /**
     * 紧后工作id列表[1;2;3]
     */
    private String backids;

    /**
     * 关键工作
     */
    private Boolean KEY;

    /**
     * 工作最早开始时间 Earliest Start
     */
    private Double ES;
    private Double _ES;
    /**
     * 工作最早完成时间 Earliest Finish
     */
    private Double EF;
    private Double _EF;
    /**
     * 工作最迟开始时间 Latest Start
     */
    private Double LS;
    private Double _LS;
    /**
     * 工作最迟完成时间 Latest Finish
     */
    private Double LF;
    private Double _LF;
    /**
     * 工作总时差 Total Float
     */
    private Double TF;
    private Double _TF;
    /**
     * 工作自由时差 Free Float
     */
    private Double FF;
    private Double _FF;
    /**
     * 工作计划持续时间 Duration Plan
     */
    private Double DP;
    private Double _DP;
    /**
     * 工作计算持续时间 Duration Calculate
     */
    private Double DC;
    private Double _DC;
    /**
     * 工作与其他工作的时间间隔 Time Lag
     */
    private Double TL;
    private Double _TL;

    /**
     * 工作计划开始时间 Plan Begin
     */
    private Double PB;
    private String _PB;
    /**
     * 工作计划完成时间 Plan End
     */
    private Double PE;
    private String _PE;
    /**
     * 工作计划时间间隔 Plan Gap
     */
    private Double PG;
    private Double _PG;

    /**
     * 工作预计开始时间 Estimate Begin
     */
    private Double EB;
    private String _EB;
    /**
     * 工作预计完成时间 Estimate End
     */
    private Double EE;
    private String _EE;
    /**
     * 工作预计时间间隔 Estimate Gap
     */
    private Double EG;
    private Double _EG;

    /**
     * 工作实际开始时间 Actual Begin
     */
    private Double AB;
    private String _AB;
    /**
     * 工作实际完成时间 Actual End
     */
    private Double AE;
    private String _AE;
    /**
     * 工作实际时间间隔 Actual Gap
     */
    private Double AG;
    private String _AG;

    /**
     * 工作完成状态 0未完成 1进行中 3已完成 Status Finish
     */
    private Integer SF;
    /**
     * 分级预警状态 0关键工作延迟小于7天大于0天 1关键工作延迟大于等于7天小于15天 2关键工作延迟大于等于15天小于30天 Status Key
     */
    private Integer SK;
    /**
     * 工序预警状态 0实际工期符合计划工期 1关键工作实际工期延迟 2非关键工作实际工期延迟 Status Work
     */
    private Integer SW;

    /**
     * 里程碑
     */
    private Boolean milepost;

    /**
     * 工作备注
     */
    private String info;

    /**
     * 紧前入度
     */
    private Integer frontsize;
    /**
     * 紧后入度
     */
    private Integer backsize;

    /**
     * 紧前工作id列表
     */
    private List<Integer> idsfront;
    /**
     * 紧前工作id列表
     */
    private List<Integer> idsback;
    /**
     * 紧前工作列表
     */
    private List<NetPlanItem> fronts;
    /**
     * 紧后工作列表
     */
    private List<NetPlanItem> backs;
    /**
     * 工作子级信息
     */
    private List<NetPlanItem> children;


    public NetPlanItem() {
        this.id=-1;
        this.rootid=-1;
        this.level=0;

        this.work="";

        this.frontids="[]";
        this.backids="[]";

        this.KEY=false;

        this.ES=-1d;
        this.EF=-1d;
        this.LS=-1d;
        this.LF=-1d;
        this.TF=0d;
        this.FF=0d;
        this.DP=0d;
        this.DC=0d;
        this.TL=0d;

        this.PB=-1d;
        this.PE=-1d;
        this.PG=0d;

        this.EB=-1d;
        this.EE=-1d;
        this.EG=0d;

        this.AB=-1d;
        this.AE=-1d;
        this.AG=0d;

        this.SF=-1;
        this.SK=-1;
        this.SW=-1;

        this.milepost=false;

        this.info="";

        this.frontsize=-1;
        this.backsize=-1;

        this.idsfront = new ArrayList<>();
        this.idsback = new ArrayList<>();
        this.fronts = new ArrayList<>();
        this.backs = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public NetPlanItem(Integer id, Integer rootid, Integer level, String work, String frontids, Double DP) {
        this.id=id;
        this.rootid=rootid;
        this.level=level;

        this.work=work;

        this.frontids=frontids;
        this.backids="[]";

        this.KEY=false;

        this.ES=-1d;
        this.EF=-1d;
        this.LS=-1d;
        this.LF=-1d;
        this.TF=0d;
        this.FF=0d;
        this.DP=DP;
        this.DC=0d;
        this.TL=0d;

        this.PB=-1d;
        this.PE=-1d;
        this.PG=0d;

        this.EB=-1d;
        this.EE=-1d;
        this.EG=0d;

        this.AB=-1d;
        this.AE=-1d;
        this.AG=0d;

        this.SF=-1;
        this.SK=-1;
        this.SW=-1;

        this.milepost=false;
        this.info="";

        this.idsfront=idsFromString(frontids);
        this.idsback = new ArrayList<>();

        this.frontsize=idsfront.size();
        this.backsize=-1;

        this.fronts = new ArrayList<>();
        this.backs = new ArrayList<>();
        this.children = new ArrayList<>();
    }


    @Override
    public NetPlanItem clone() throws CloneNotSupportedException {
        NetPlanItem netPlanItem = (NetPlanItem) super.clone();
        return CloneUtils.cloneTo(netPlanItem);
    }


    /**
     * 设置计算参数
     *
     * @param ES
     * @param EF
     * @param LS
     * @param LF
     * @param TF
     * @param FF
     * @param KEY
     */
    public void sumParams(Double ES, Double EF, Double LS, Double LF, Double TF, Double FF, Boolean KEY) {
        if(this.ES > ES) {
            this.ES=ES;
        }
        if(this.EF < EF) {
            this.EF=EF;
        }
        if(this.LS > LS) {
            this.LS=LS;
        }
        if(this.LF < LF) {
            this.LF=LF;
        }

        if(this.TF > TF) {
            this.TF=TF;
        }
        if(this.FF > FF) {
            this.FF=FF;
        }
        if(!this.KEY && KEY) {
            this.KEY=true;
        }
    }

    public void setParams(Double ES, Double EF, Double LS, Double LF, Double TF, Double FF, Boolean KEY) {
        this.ES=ES;
        this.EF=EF;
        this.LS=LS;
        this.LF=LF;
        this.TF=TF;
        this.FF=FF;
        this.KEY=KEY;
    }


    /**
     * 添加紧前紧后关系
     *
     * @param id
     */
    public void addFrontLinker(Integer id) {
        List<Integer> ids = idsFromString(frontids);

        if(frontids.length() <= 2) {
            frontids = String.format("[%d]",id);
        } else if(!ids.contains(id)) {
            frontids = String.format("%s;%d]",frontids.substring(0,frontids.length()-1),id);
        }
    }

    public void addBackLinker(Integer id) {
        List<Integer> ids = idsFromString(backids);

        if(backids.length() <= 2) {
            backids = String.format("[%d]",id);
        } else if(!ids.contains(id)) {
            backids = String.format("%s;%d]",backids.substring(0,backids.length()-1),id);
        }
    }

    public NetPlanItem display() {
        this._ES=round(ES,2);
        this._EF=round(EF,2);
        this._LS=round(LS,2);
        this._LF=round(LF,2);
        this._TF=round(TF,2);
        this._FF=round(FF,2);
        this._DP=round(DP,2);
        this._DC=round(DC,2);
        this._TL=round(TL,2);

        this._PB= DateUtils.formatExcelDateTime(PB, DateUtils.DATETIME_FORMAT.YEAR_MINUTES);
        this._PE=DateUtils.formatExcelDateTime(PE, DateUtils.DATETIME_FORMAT.YEAR_MINUTES);
        this._PG=round(PG,2);

        this._EB=DateUtils.formatExcelDateTime(EB, DateUtils.DATETIME_FORMAT.YEAR_MINUTES);
        this._EE=DateUtils.formatExcelDateTime(EE, DateUtils.DATETIME_FORMAT.YEAR_MINUTES);
        this._EG=round(EG,2);

        this._AB=AB<0?"":DateUtils.formatExcelDateTime(AB, DateUtils.DATETIME_FORMAT.YEAR_MINUTES);
        this._AE=AE<0?"":DateUtils.formatExcelDateTime(AE, DateUtils.DATETIME_FORMAT.YEAR_MINUTES);
        this._AG=AG>0?Double.toString(round(AG,2)):"";

        return this;
    }

    /**
     * 四舍五入
     * @param value
     * @param scale
     * @return
     */
    public static double round(Double value, Integer scale){
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void initDisplayChildren(NetPlanItem item) {
        item.display();

        List<NetPlanItem> list = item.getChildren();
        if(!list.isEmpty()) {
            for(NetPlanItem x: list) {
                initDisplayChildren(x);
            }
        }
    }

    /**
     * 以分号拆分字符数组
     * @param ids
     * @return
     */
    public static List<Integer> idsFromString(String ids) {
        String _ids = ids;
        _ids = _ids.replace("[","");
        _ids = _ids.replace("]","");
        _ids = _ids.replace(",",";");

        String[] strs = _ids.split(";");

        List<Integer> item_ids = new ArrayList<>();
        for (String str : strs) {
            if (!str.isEmpty() && RegexUtil.isNumber(str)) {
                item_ids.add(Integer.parseInt(str));
            }
        }

        return item_ids;
    }


    /**
     * 结构体字段
     *
     * @return
     */
    public Pair<String, String>[] colsFromStruct() {
        Pair<String, String>[] columns = new Pair[30];

        columns[0] = new Pair<>("id","工作ID");
        columns[1] = new Pair<>("rootid","工作父ID");
        columns[2] = new Pair<>("level","工作层级");
        columns[3] = new Pair<>("work","工作名称");
        columns[4] = new Pair<>("frontids","紧前工作");
        columns[5] = new Pair<>("backids","紧后工作");
        columns[6] = new Pair<>("KEY","关键工作");
        columns[7] = new Pair<>("ES","最早开始");
        columns[8] = new Pair<>("EF","最早完成");
        columns[9] = new Pair<>("LS","最迟开始");
        columns[10] = new Pair<>("LF","最迟完成");
        columns[11] = new Pair<>("TF","总时差");
        columns[12] = new Pair<>("FF","自由时差");
        columns[13] = new Pair<>("DP","计划工效");
        columns[14] = new Pair<>("DC","计算工效");
        columns[15] = new Pair<>("TL","时间间隔");
        columns[16] = new Pair<>("PB","计划开始");
        columns[17] = new Pair<>("PE","计划完成");
        columns[18] = new Pair<>("PG","计划工期");
        columns[19] = new Pair<>("EB","预计开始");
        columns[20] = new Pair<>("EE","预计完成");
        columns[21] = new Pair<>("EG","预计工期");
        columns[22] = new Pair<>("AB","实际开始");
        columns[23] = new Pair<>("AE","实际完成");
        columns[24] = new Pair<>("AG","实际工期");
        columns[25] = new Pair<>("SF","完成状态");
        columns[26] = new Pair<>("SK","分级预警");
        columns[27] = new Pair<>("SW","工序预警");
        columns[28] = new Pair<>("milepost","里程碑");
        columns[29] = new Pair<>("info","备注");

        return columns;
    }


    /**
     * 按（id）排序
     *
     * @param netPlanItemList
     */
    public static void sortNetPlanItemListAsc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getId));
        }
    }
    public static void sortNetPlanItemListDesc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getId).reversed());
        }
    }
    public static void sortPairNetPlanItemListAsc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getId().compareTo(value2.getId());
                }
            });
        }
    }
    public static void sortPairNetPlanItemListDesc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getId().compareTo(value2.getId());
                }
            }.reversed());
        }
    }


    /**
     * 按（RootId）排序
     *
     * @param netPlanItemList
     */
    public static void sortNetPlanItemListOfRootIdAsc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getRootid));
        }
    }
    public static void sortNetPlanItemListOfRootIdDesc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getRootid).reversed());
        }
    }
    public static void sortPairNetPlanItemListOfRootIdAsc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getRootid().compareTo(value2.getRootid());
                }
            });
        }
    }
    public static void sortPairNetPlanItemListOfRootIdDesc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getRootid().compareTo(value2.getRootid());
                }
            }.reversed());
        }
    }


    /**
     * 按（ES）排序
     *
     * @param netPlanItemList
     */
    public static void sortNetPlanItemListOfESAsc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getES));
        }
    }
    public static void sortNetPlanItemListOfESDesc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getES).reversed());
        }
    }
    public static void sortPairNetPlanItemListOfESAsc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getES().compareTo(value2.getES());
                }
            });
        }
    }
    public static void sortPairNetPlanItemListOfESDesc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getES().compareTo(value2.getES());
                }
            }.reversed());
        }
    }


    /**
     * 按（LF）排序
     *
     * @param netPlanItemList
     */
    public static void sortNetPlanItemListOfEFAsc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getEF));
        }
    }
    public static void sortNetPlanItemListOfEFDesc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getEF).reversed());
        }
    }
    public static void sortPairNetPlanItemListOfEFAsc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getEF().compareTo(value2.getEF());
                }
            });
        }
    }
    public static void sortPairNetPlanItemListOfEFDesc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getEF().compareTo(value2.getEF());
                }
            }.reversed());
        }
    }


    /**
     * 按（PB）排序
     *
     * @param netPlanItemList
     */
    public static void sortNetPlanItemListOfPBAsc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getPB));
        }
    }
    public static void sortNetPlanItemListOfPBDesc(List<NetPlanItem> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(Comparator.comparing(NetPlanItem::getPB).reversed());
        }
    }
    public static void sortPairNetPlanItemListOfPBAsc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getPB().compareTo(value2.getPB());
                }
            });
        }
    }
    public static void sortPairNetPlanItemListOfPBDesc(List<Pair<Integer, NetPlanItem>> netPlanItemList) {
        if (netPlanItemList != null) {
            netPlanItemList.sort(new Comparator<Pair<Integer, NetPlanItem>>() {

                @Override
                public int compare(Pair<Integer, NetPlanItem> o1, Pair<Integer, NetPlanItem> o2) {
                    NetPlanItem value1 = o1.getValue();
                    NetPlanItem value2 = o2.getValue();
                    return value1.getPB().compareTo(value2.getPB());
                }
            }.reversed());
        }
    }
}
