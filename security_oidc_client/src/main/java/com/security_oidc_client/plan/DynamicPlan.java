package com.security_oidc_client.plan;

import com.security_oidc_client.domain.DateUtils;
import com.security_oidc_client.service.dto.CloneUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.*;
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
@Component
public class DynamicPlan implements Cloneable {
    /**
     * 计划工期 Tp
     */
    private Double time_plan;
    /**
     * 要求工期 Tr
     */
    private Double time_request;
    /**
     * 计算工期 Tc
     */
    private Double time_calculate;

    /**
     * 最早开始时间 ES
     */
    private Double earliest_start;
    /**
     * 最迟完成时间 LF
     */
    private Double latest_finish;

    /**
     * 最大级数
     */
    private Integer max_level;

    /**
     * 网络计划整体列表
     */
    private Map<Integer, NetPlanItem> items_map;
    /**
     * 网络计划单层列表
     */
    private List<NetPlanItem> items_list;
    /**
     * 网络计划拓扑列表
     */
    private Queue<NetPlanItem> items_queue;


    public DynamicPlan() {
        this.time_plan=-1d;
        this.time_request=-1d;
        this.time_calculate=-1d;

        this.earliest_start=-1d;
        this.latest_finish=-1d;

        this.max_level=0;

        this.items_map=new HashMap<>();
        this.items_list=new ArrayList<>();
        this.items_queue = new LinkedList<>();
    }

    public DynamicPlan(Double ES) {
        this.time_plan=-1d;
        this.time_request=-1d;
        this.time_calculate=-1d;

        this.earliest_start=ES;
        this.latest_finish=-1d;

        this.max_level=0;

        this.items_map=new HashMap<>();
        this.items_list=new ArrayList<>();
        this.items_queue = new LinkedList<>();
    }

    public DynamicPlan(Double ES, Double LF) {
        this.time_plan=-1d;
        this.time_request=-1d;
        this.time_calculate=-1d;

        this.earliest_start = ES;
        this.latest_finish = LF;

        this.max_level=0;

        this.items_map=new HashMap<>();
        this.items_list=new ArrayList<>();
        this.items_queue = new LinkedList<>();
    }


    @Override
    public DynamicPlan clone() throws CloneNotSupportedException {
        DynamicPlan dynamicPlan = (DynamicPlan) super.clone();
        return CloneUtils.cloneTo(dynamicPlan);
    }

    /**
     * 初始化网络计划
     * @param items
     * @return
     */
    public Integer initItems(List<NetPlanItem> items) {
        items_map.clear();
        for(NetPlanItem item: items) {
            items_map.put(item.getId(), item);

            if(item.getLevel() > max_level) {
                max_level = item.getLevel();
            }
        }

        return items_map.size();
    }

    public Integer initItems(List<NetPlanItem> items, Double ES) {
        this.earliest_start = ES;
        items_map.clear();
        for(NetPlanItem item: items) {
            items_map.put(item.getId(), item);

            if(item.getLevel() > max_level) {
                max_level = item.getLevel();
            }
        }

        return items_map.size();
    }

    public void initItems(NetPlanItem[] items, Integer size) {
        items_map.clear();
        for(NetPlanItem item: items) {
            items_map.put(item.getId(), item);

            if(item.getLevel() > max_level) {
                max_level = item.getLevel();
            }
        }

        items_map.size();
    }

    public void initItems(NetPlanItem[] items, Integer size, Double ES) {
        this.earliest_start = ES;
        items_map.clear();
        for(NetPlanItem item: items) {
            items_map.put(item.getId(), item);

            if(item.getLevel() > max_level) {
                max_level = item.getLevel();
            }
        }

        items_map.size();
    }


    /**
     * 初始化网络计划实际时间
     *
     * @param items
     * @return
     */
    public Integer initActualItems(List<NetPlanItem> items) {
        for(NetPlanItem item: items) {
            NetPlanItem _item = items_map.get(item.getId());
            _item.setAB(item.getAB());
            _item.setAE(item.getAE());
            _item.setAG(_item.getAE()-_item.getAB());
        }

        return items_map.size();
    }

    public Integer initActualItems(NetPlanItem[] items, Integer size) {
        for(NetPlanItem item: items) {
            NetPlanItem _item = items_map.get(item.getId());
            _item.setAB(item.getAB());
            _item.setAE(item.getAE());
            _item.setAG(_item.getAE()-_item.getAB());
        }

        return items_map.size();
    }


    /**
     * 获取网络计划列表
     * @return
     */
    public List<Pair<Integer, NetPlanItem>> getItems() {
        List<Pair<Integer, NetPlanItem>> pair_list = new ArrayList<>();
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            pair_list.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        NetPlanItem.sortPairNetPlanItemListAsc(pair_list);

        return pair_list;
    }

    /**
     * 获取网络计划列表
     * @return
     */
    public List<NetPlanItem> getItemList() {
        List<NetPlanItem> list = new ArrayList<>();
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            list.add(entry.getValue().display());
        }

        return list;
    }

    /**
     * 获取网络计划列表
     * @return
     */
    public List<NetPlanItem> getItemTree() {
        List<NetPlanItem> list = new ArrayList<>();
        NetPlanItem item  = items_map.get(1);
        NetPlanItem.initDisplayChildren(item);
        list.add(item);
        return list;
    }


    /**
     * 获取网络计划中关键线路
     * @return
     */
    public List<NetPlanItem> getKeyItems() {
        List<NetPlanItem> items_key = new ArrayList<>();
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            NetPlanItem item = entry.getValue();
            if(item.getKEY()) {
                items_key.add(item.display());
            }
        }

        return items_key;
    }

    /**
     * 静态网络计划计算
     * 双代号时标网络计划计算为单层级联动计算。
     * 第一步对网络计划列表按照最早开始时间（ES）进行升序排序。
     * 第二步从头到尾依次计算最早完成时间（EF）= 最早开始时间（ES）+ 工作计划持续时间（DP）。
     * 第三步计算总工期Tc。
     * 第四步从尾到头依次计算最迟开始时间（LS）= 最迟完成时间（LF）- 工作计划持续时间（DP）。
     * 第五步从头到尾依次计算总时差（TF）= 最迟开始时间（LS） - 最早开始时间（ES）或 最迟完成时间（LF） - 最早完成时间（EF）。
     * 第六步从头到尾依次计算自由时差（FF）= 紧后工作最早开始时间（ES）- 当前工作最早完成时间（EF）。
     * 第七步从头到尾找出总时差（TF）和 自由时差（FF）均为0的工作作为关键线路。
     */
    public void calcPlanItems() {
        // 1-2、从头到尾获取最大层级网络并计算最早完成时间ES | EF
        calcEarliestTopology();

        // 3、从头到尾计算总工期Tc
        calcTimeCalculate();

        // 4、从尾到头计算最迟完成时间LS
        calcLatestTopology();

        // 5、从头到尾计算总时差TF|计算自由时差FF|计算关键工作KEY
        calcFloatAndKey();

        // 6、从头到尾逐级向上汇总树形层级结构时间参数
        clearSumTreeTime();
        sumTreeTime();

        // 7、复制最早计划为计划时间
        copyPlanTime();
    }

    /**
     * 动态网络计划计算
     *
     * @param id
     * @param type
     * @param value
     */
    public void calcEstimateItems(Integer id, Integer type, Double value) {
        // 1、获取指定工作以后的最大层级网络
        NetPlanItem item = items_map.get(id);

        switch (type) {
            case 2: {// 传入value 为 ES
                Double TL = Math.abs(value-item.getES());
                int frontsize = item.getIdsfront().size();
                if(value < item.getES() && frontsize > 0) {
                    if (TL < item.getTL()) {
                        item.setES(item.getES() - TL);
                        item.setTL(item.getTL() - TL);
                    } else {
                        item.setES(item.getES() - item.getTL());
                        item.setTL(0d);
                    }
                } else if(value < item.getES() && frontsize == 0) {
                    item.setES(item.getES() - TL);
                    item.setTL(0d);
                } else {
                    item.setES(item.getES() + TL);
                    item.setTL(item.getTL() + TL);
                }

                item.setEF(item.getES()+item.getDP());
            }
            break;
            case 3: // 传入value 为 DP
                item.setDP(value);
                item.setEF(item.getES()+item.getDP());
                break;
            default: // 传入value 为 EF
                item.setEF(value);
                item.setDP(item.getEF()-item.getES());
                break;
        }

        items_list.clear();
        initBackItems(item);
        uniqueItems();
        items_list.add(0,item);

        // 2、从头到尾计算最早完成时间ES
        calcEarliestStart();

        // 3、从头到尾计算总工期Tc
        calcTimeCalculate();

        // 4、从尾到头计算最迟完成时间LS
        filterItems(item.getLevel(), "PBAsc", false, false, false);
        clearLatestFinish();
        calcLatestFinish();

        // 5、从头到尾计算总时差TF|计算自由时差FF|计算关键工作KEY
        calcFloatAndKey();

        // 6、从头到尾逐级向上汇总树形层级结构时间参数
        clearSumTreeTime();
        sumTreeTime();

        // 7、复制最早计划为计划时间
        copyEstimateTime();
    }

    /**
     * 动态网络计划计算
     *
     * @param id
     * @param value
     */
    public void calcActualItems(Integer id, Double value) {
        calcEstimateItems(id, 1, value);

        NetPlanItem item = items_map.get(id);

        if (item.getAB() < 0) {
            item.setAB(item.getEB());
        }
        item.setAE(value);
        item.setAG(item.getAE()-item.getAB());
    }

    /**
     * 计算工作状态
     *
     * @param actual
     */
    public void calcStatus(Boolean actual) {
        double now = DateUtils.currentExcelDateTime();
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            NetPlanItem item = entry.getValue();
            calcChildrenStatus(item, now, actual);
        }
    }


    /**
     * 导出CSV文件
     * @param path
     * @return
     */
    public void exportItems(String path) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path)),"GBK"))) {
            String str = "id,rootid,level,work,KEY,frontids,backids,ES,EF,LS,LF,TF,FF,DP,DC,TL,PB,PE,PG,EB,EE,EG,AB,AE,AG,SF,SK,SW,milepost\n";
            bufferedWriter.write(str);

            for(Pair<Integer, NetPlanItem> x: getItems()) {
                NetPlanItem item = x.getValue();

                str = String.format("%d,%d,%d,%s,%b,%s,%s,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%s,%s,%.3f,%s,%s,%.3f,%s,%s,%s,%d,%s,%d,%b\n",item.getId(),item.getRootid(),item.getLevel(),
                        item.getWork(),item.getKEY(),item.getFrontids(),item.getBackids(),
                        item.getES(),item.getEF(),item.getLS(),item.getLF(),item.getTF(),item.getFF(),item.getDP(),item.getDC(),item.getTL(),
                        DateUtils.formatExcelDateTime(item.getPB(), DateUtils.DATETIME_FORMAT.DEFAULT),DateUtils.formatExcelDateTime(item.getPE(), DateUtils.DATETIME_FORMAT.DEFAULT),item.getPG(),
                        DateUtils.formatExcelDateTime(item.getEB(), DateUtils.DATETIME_FORMAT.DEFAULT),DateUtils.formatExcelDateTime(item.getEE(), DateUtils.DATETIME_FORMAT.DEFAULT),item.getEG(),
                        item.getAB() < 0 ? "" : DateUtils.formatExcelDateTime(item.getAB(), DateUtils.DATETIME_FORMAT.DEFAULT),
                        item.getAE() < 0 ? "" : DateUtils.formatExcelDateTime(item.getAE(), DateUtils.DATETIME_FORMAT.DEFAULT),
                        item.getAG() > 0 ? toString(item.getAG(),3) : "",
                        item.getSF(),item.getSK() < 0 ? "" : item.getSK().toString(),item.getSW(),item.getMilepost());

                bufferedWriter.write(str);
            }
        }
    }


    /**
     * 格式化浮点数
     * @param value
     * @param precision
     * @return
     */
    public static String toString(Double value, Integer precision) {
        String format = "%"+String.format(".%df", precision);
        return String.format(format, value);
    }


    /**
     * 二分查找工作
     *
     * @param id
     * @return
     */
    private NetPlanItem findItem(Integer id) {
        int left = 0;
        int right = items_list.size()-1;
        while(left <= right) {
            int mid = (left+right)/2;
            int _id = items_list.get(mid).getId();

            if(_id == id) {
                return items_list.get(mid);
            }

            if(_id > id) {
                right = mid-1;
            } else {
                left = mid+1;
            }
        }

        return items_list.get(0);
    }


    /**
     * 工作列表排序
     *
     * @param sort
     * @return
     */
    private Map<Integer, NetPlanItem> sortItemMap(String sort) {
        List<Pair<Integer, NetPlanItem>> pair_list = new ArrayList<>();
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            pair_list.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        switch (sort) {
            case "IDAsc":
                NetPlanItem.sortPairNetPlanItemListAsc(pair_list);
                break;
            case "IDDesc":
                NetPlanItem.sortPairNetPlanItemListDesc(pair_list);
                break;
            case "RIDAsc":
                NetPlanItem.sortPairNetPlanItemListOfRootIdAsc(pair_list);
                break;
            case "RIDDesc":
                NetPlanItem.sortPairNetPlanItemListOfRootIdDesc(pair_list);
                break;
            case "ESAsc":
                NetPlanItem.sortPairNetPlanItemListOfESAsc(pair_list);
                break;
            case "ESDesc":
                NetPlanItem.sortPairNetPlanItemListOfESDesc(pair_list);
                break;
            case "EFAsc":
                NetPlanItem.sortPairNetPlanItemListOfEFAsc(pair_list);
                break;
            case "EFDesc":
                NetPlanItem.sortPairNetPlanItemListOfEFDesc(pair_list);
                break;
            case "PBAsc":
                NetPlanItem.sortPairNetPlanItemListOfPBAsc(pair_list);
                break;
            case "PBDesc":
                NetPlanItem.sortPairNetPlanItemListOfPBDesc(pair_list);
                break;
            default:
                break;
        }

        items_map.clear();
        for (Pair<Integer, NetPlanItem> pair : pair_list) {
            items_map.put(pair.getKey(), pair.getValue());
        }

        return items_map;
    }

    private void sortItemList(String sort) {
        switch (sort) {
            case "IDAsc":
                NetPlanItem.sortNetPlanItemListAsc(items_list);
                break;
            case "IDDesc":
                NetPlanItem.sortNetPlanItemListDesc(items_list);
                break;
            case "RIDAsc":
                NetPlanItem.sortNetPlanItemListOfRootIdAsc(items_list);
                break;
            case "RIDDesc":
                NetPlanItem.sortNetPlanItemListOfRootIdDesc(items_list);
                break;
            case "ESAsc":
                NetPlanItem.sortNetPlanItemListOfESAsc(items_list);
                break;
            case "ESDesc":
                NetPlanItem.sortNetPlanItemListOfESDesc(items_list);
                break;
            case "EFAsc":
                NetPlanItem.sortNetPlanItemListOfEFAsc(items_list);
                break;
            case "EFDesc":
                NetPlanItem.sortNetPlanItemListOfEFDesc(items_list);
                break;
            case "PBAsc":
                NetPlanItem.sortNetPlanItemListOfPBAsc(items_list);
                break;
            case "PBDesc":
                NetPlanItem.sortNetPlanItemListOfPBDesc(items_list);
                break;
            default:
                break;
        }
    }


    /**
     * 获取网络计划对应的层级列表
     *
     * @param level
     * @param sort
     * @param topology
     * @param earliest
     * @param backs
     */
    private void filterItems(Integer level, String sort, Boolean topology, Boolean earliest, Boolean backs) {
        items_queue.clear();
        items_list.clear();

        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            NetPlanItem item = entry.getValue();

            item.setIdsfront(NetPlanItem.idsFromString(item.getFrontids()));
            item.setFrontsize(item.getIdsfront().size());

            if(item.getLevel().equals(level)) {
                items_list.add(item);

                if(topology && earliest && item.getFrontsize() == 0) {
                    item.setES(earliest_start);
                    item.setEF(item.getES()+item.getDP());
                    items_queue.add(item);
                }

                if(topology && !earliest && item.getBacksize() == 0) {
                    item.setLF(latest_finish);
                    item.setLS(item.getLF()-item.getDP());
                    items_queue.add(item);
                }

                if (backs) {
                    copyFrontToBackLinker(item);
                }
            }

            if(level == 0) {
                items_list.add(item);

                if(topology && earliest && item.getFrontsize() == 0) {
                    item.setES(earliest_start);
                    item.setEF(item.getES()+item.getDP());
                    items_queue.add(item);
                }

                if(topology && !earliest && item.getBacksize() == 0) {
                    item.setLF(latest_finish);
                    item.setLS(item.getLF()-item.getDP());
                    items_queue.add(item);
                }

                if (backs) {
                    copyFrontToBackLinker(item);
                }
            }
        }

        sortItemList(sort);
    }

    /**
     * 网络计划去重
     */
    private void uniqueItems() {
//        items_list = items_list.stream().distinct().collect(Collectors.toList());
        LinkedHashSet<NetPlanItem> items_set = new LinkedHashSet<>(items_list);
        items_list.clear();
        items_list = new ArrayList<>(items_set);

        sortItemList("PBAsc");
    }


    /**
     * 计算最早开始时间EF
     * 工作最早开始时间计算顺序应从起点节点开始，顺着箭线方向依次逐项计算
     * 最早开始时间（ES）等于各紧前工作的最早完成时间（EF）的最大值
     */
    private void calcEarliestStart() {
        for (NetPlanItem x : items_list) {
            NetPlanItem item = items_map.get(x.getId());

            if(!item.getIdsback().isEmpty()) {
                for (Integer id : item.getIdsback()) {
                    NetPlanItem item_back = items_map.get(id);

                    if(item_back.getES() < 0) {
                        item_back.setES(item.getEF()+item_back.getTL());
                        item_back.setEF(item_back.getES()+item_back.getDP());
                    }

                    double TL = item_back.getEB()-item.getEF();
                    double _TL = item_back.getEB()-item.getEE();
                    if(item.getEF() > item_back.getES() || TL < _TL) {
                        item_back.setTL(TL < 0 ? 0 : TL);
                        item_back.setES(item.getEF()+item_back.getTL());
                        item_back.setEF(item_back.getES()+item_back.getDP());
                    }
                }
            }
        }
    }

    private List<NetPlanItem> clearEarliestStart() {
        for (NetPlanItem x : items_list) {
            NetPlanItem item = items_map.get(x.getId());

            item.setES(-1d);
            item.setEF(-1d);

            if(item.getIdsfront().isEmpty())
            {
                item.setES(earliest_start);
                item.setEF(item.getES()+item.getDP());
            }
        }

        return items_list;
    }


    /**
     * 计算最迟完成时间LF
     * 工作最迟完成时间计算顺序应从终点节点起，逆着箭线方向依次逐项计算
     * 最迟完成时间（LF）等于各紧后工作的最迟开始时间（LS）的最小值
     */
    private void calcLatestFinish() {
        int size = items_list.size();
        for (int i = size-1; i>=0; i--) {
            NetPlanItem item = items_map.get(items_list.get(i).getId());

            if(!item.getIdsfront().isEmpty()) {
                for (Integer id : item.getIdsfront()) {
                    NetPlanItem item_front = items_map.get(id);

                    if(item_front.getLF() < 0) {
                        item_front.setLF(item.getLS()-item.getTL());
                        item_front.setLS(item_front.getLF()-item_front.getDP());
                    }

                    if(item.getLS() < item_front.getLF()) {
                        item_front.setLF(item.getLS()-item.getTL());
                        item_front.setLS(item_front.getLF()-item_front.getDP());
                    }
                }
            }
        }
    }

    private void clearLatestFinish() {
        for (NetPlanItem x : items_list) {
            NetPlanItem item = items_map.get(x.getId());

            item.setLS(-1d);
            item.setLF(-1d);

            if(item.getIdsback().isEmpty())
            {
                item.setLF(latest_finish);
                item.setLS(item.getLF()-item.getDP());
            }
        }
    }


    /**
     * 获取紧前紧后工作列表
     *
     * @return
     */
    private List<NetPlanItem> getFrontBackItems() {
        for (NetPlanItem x : items_list) {
            NetPlanItem item = items_map.get(x.getId());

            if(!item.getIdsfront().isEmpty()) {
                item.getFronts().clear();
                for (Integer id : item.getIdsfront()) {
                    item.getFronts().add(items_map.get(id));
                }
            }

            if(!item.getIdsback().isEmpty()) {
                item.getBacks().clear();
                for (Integer id : item.getIdsback()) {
                    item.getBacks().add(items_map.get(id));
                }
            }
        }

        return items_list;
    }

    /**
     * 重置紧前紧后工作列表
     *
     * @param item
     */
    private void initFrontItems(NetPlanItem item) {
        if(!item.getIdsfront().isEmpty()) {
            for (Integer id : item.getIdsfront()) {
                NetPlanItem _item = items_map.get(id);

                _item.setES(-1d);
                _item.setEF(-1d);

                if(_item.getIdsfront().isEmpty()) {
                    _item.setES(earliest_start);
                    _item.setEF(_item.getES()+_item.getDP());
                }

                items_list.add(_item);

                if(!_item.getIdsfront().isEmpty()) {
                    initFrontItems(_item);
                }
            }
        }
    }

    private void initBackItems(NetPlanItem item) {
        if(!item.getIdsback().isEmpty()) {
            for (Integer id : item.getIdsback()) {
                NetPlanItem _item = items_map.get(id);

                _item.setLS(-1d);
                _item.setLF(-1d);

                if(_item.getIdsback().isEmpty()) {
                    _item.setLF(latest_finish);
                    _item.setLS(_item.getLF()-_item.getDP());
                }

                items_list.add(_item);

                if(!_item.getIdsback().isEmpty()) {
                    initBackItems(_item);
                }
            }
        }
    }


    /**
     * 计算总时差TF|计算自由时差FF|计算关键工作KEY
     * 总时差（TF）等于｜LS - ES｜或｜LF - EF｜
     * 自由时差（FF）等于紧后工作最早开始时间（ES）减本工作最早完成时间（EF），多项紧后工作取最小值
     * 计划工期等于计算工期情况下TF=0的工作为关键工作
     */
    private void calcFloatAndKey() {
        for (NetPlanItem x : items_list) {
            NetPlanItem item = items_map.get(x.getId());

            item.setTF(item.getLS()-item.getES());

            if(!item.getIdsback().isEmpty()) {
                item.setFF(items_map.get(item.getIdsback().get(0)).getES()-item.getEF());
                for(int i = 1; i<item.getIdsback().size(); i++) {
                    NetPlanItem item_back = items_map.get(item.getIdsback().get(i));
                    if(item_back.getES()-item.getEF() < item.getFF()) {
                        item.setFF(item_back.getES()-item.getEF());
                    }
                }
            } else {
                item.setFF(latest_finish-item.getEF());
            }

            item.setKEY(item.getTF()==0);
        }
    }

    private void clearFloatAndKey() {
        for (NetPlanItem x : items_list) {
            NetPlanItem item = items_map.get(x.getId());

            item.setKEY(false);
            item.setTF(0d);
            item.setFF(0d);
            item.setDC(0d);;
        }
    }


    /**
     * 计算总工期Tc
     *
     */
    private void calcTimeCalculate() {
        latest_finish = 0d;
        for (NetPlanItem x : items_list) {
            NetPlanItem item = items_map.get(x.getId());

            if(item.getEF() > latest_finish) {
                latest_finish = item.getEF();
            }
        }

        time_calculate = latest_finish - earliest_start;
    }


    /**
     * 逐级向上汇总树形层级结构时间参数
     *
     */
    private void sumTreeTime() {
        // 8、Sum ES、EF、LS、LF、TF、FF、KEY。
        for(int i = max_level-1; i>0; i--) {
            filterItems(i+1, "IDAsc", false, false, false);

            for (NetPlanItem x : items_list) {
                NetPlanItem item = items_map.get(x.getId());
                NetPlanItem item_root = items_map.get(x.getRootid());

                if(item.getRootid().equals(item_root.getId())) {
                    item_root.getChildren().add(item);
                }
            }

            filterItems(i, "IDAsc", false, false, false);
            for (NetPlanItem x : items_list) {
                NetPlanItem item = items_map.get(x.getId());

                List<NetPlanItem> items = item.getChildren();
                if (!items.isEmpty()) {
                    NetPlanItem _item = items.get(0);
                    item.setParams(_item.getES(),_item.getEF(),_item.getLS(),_item.getLF(),_item.getTF(), _item.getFF(), _item.getKEY());

                    for(int j = 1; j<items.size(); j++) {
                        _item = items.get(j);
                        item.sumParams(_item.getES(),_item.getEF(),_item.getLS(),_item.getLF(),_item.getTF(), _item.getFF(), _item.getKEY());
                    }
                }
            }
        }

    }

    private void clearSumTreeTime() {
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            NetPlanItem item = entry.getValue();

            if (item.getLevel() < max_level) {
                item.setES(-1d);
                item.setEF(-1d);
                item.setLS(-1d);
                item.setLF(-1d);

                item.setKEY(false);
                item.setTF(0d);
                item.setFF(0d);
                item.setDC(0d);

                item.setSF(-1);
                item.setSK(-1);
                item.setSW(-1);

                item.getChildren().clear();
            }
        }
    }


    /**
     * 计算工作状态 完成状态（SF）| 分级预警（SK）| 进度预警（SW）
     *
     * @param item
     * @param now
     * @param actual
     */
    private void calcChildrenStatus(NetPlanItem item, Double now, Boolean actual) {
        double ES = item.getES();
        double EF = item.getEF();

        double AB = item.getAB();
        double AE = item.getAE();

        // 计算所有工作的施工状态（SF）
        if(ES > now) {
            item.setSF(0);
        } else if(EF > now) {
            item.setSF(1);
            if (AB < 0) {
                item.setAB(item.getEB());
            }
        } else if(EF <= now) {
            item.setSF(3);
            if (AB < 0) {
                item.setAB(item.getEB());
            }
            if (AE < 0) {
                item.setAE(item.getEE());
            }
            item.setAG(item.getAE()-item.getAB());

            if (AE < 0 && actual) {
                item.setSF(1);
                item.setAG(0d);
                item.setDP(now-ES);
            }
        } else {
            item.setSF(2);
        }

        // 计算未开始关键线路上的工作的分级预警（SK）
        if(item.getKEY()) {
            double gap = item.getEE()-item.getPG();
            if(item.getSF() < 3 && gap >= 7 && gap < 15) {
                item.setSK(1);
            }
            if(item.getSF() < 3 && gap >= 15 && gap < 30) {
                item.setSK(2);
            }
            if(item.getSF() < 3 && gap >= 30) {
                item.setSK(3);
            } else {
                item.setSK(0);
            }
        } else {
            item.setSK(-1);
        }

        // 计算进行中的工作的进度预警（SW）
        double AG = now-item.getAB();
        if(item.getSF() == 1 && AG > item.getPG() && item.getKEY()) {
            item.setSW(1);
        }
        if(item.getSF() == 1 && AG > item.getPG() && !item.getKEY()) {
            item.setSW(2);
        } else {
            item.setSW(0);
        }

        List<NetPlanItem> items = item.getChildren();
        if(!items.isEmpty()) {
            for(NetPlanItem x: items) {
                calcChildrenStatus(x, now, actual);
            }
        }
    }


    /**
     * 复制最早计划为计划时间
     */
    private void copyPlanTime() {
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            NetPlanItem item = entry.getValue();

            item.setPB(item.getES());
            item.setPE(item.getEF());
            item.setPG(item.getEF()-item.getES());
        }
    }

    /**
     * 复制最早计划为预计时间
     */
    private void copyEstimateTime() {
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            NetPlanItem item = entry.getValue();

            item.setEB(item.getES());
            item.setEE(item.getEF());
            item.setEG(item.getEF()-item.getES());
        }

    }

    /**
     * 复制紧前关系到紧后关系
     *
     * @param item
     */
    private void copyFrontToBackLinker(NetPlanItem item) {
        for (Integer x : item.getIdsfront()) {
            NetPlanItem item_front = items_map.get(x);

            item_front.addBackLinker(item.getId());
        }
    }

    private Map<Integer, NetPlanItem> copyFrontToBackLinker() {
        for (Map.Entry<Integer, NetPlanItem> entry : items_map.entrySet()) {
            NetPlanItem item = entry.getValue();

            copyFrontToBackLinker(item);
        }

        return items_map;
    }


    /**
     * 网络拓扑排序计算最早时间
     */
    private void calcEarliestTopology() {
        filterItems(max_level, "IDAsc", true, true, true);

        while (!items_queue.isEmpty()) {
            NetPlanItem item = items_map.get(items_queue.poll().getId());

            item.setIdsback(NetPlanItem.idsFromString(item.getBackids()));
            item.setBacksize(item.getIdsback().size());
            if(!item.getIdsback().isEmpty()) {
                for (Integer x : item.getIdsback()) {
                    NetPlanItem item_back = items_map.get(x);

                    item_back.setFrontsize(item_back.getFrontsize()-1);
                    if(item_back.getFrontsize() == 0) {
                        items_queue.offer(item_back);
                    }
                }
            }

            if(!item.getIdsfront().isEmpty()) {
                for (Integer x : item.getIdsfront()) {
                    NetPlanItem item_front = items_map.get(x);

                    item_front.setEF(item_front.getES() + item_front.getDP());
                    if(item.getES() < 0 || item_front.getEF() > item.getES()) {
                        item.setES(item_front.getEF()+item.getTL());
                        item.setEF(item.getES()+item.getDP());
                    }
                }
            }
        }

        filterItems(max_level, "ESAsc", false, false, false);
    }

    /**
     * 网络拓扑排序计算最迟时间
     *
     */
    private void calcLatestTopology() {
        filterItems(max_level, "IDAsc", true, false, false);

        while (!items_queue.isEmpty()) {
            NetPlanItem item = items_map.get(items_queue.poll().getId());

            if(!item.getIdsfront().isEmpty()) {
                for (Integer x : item.getIdsfront()) {
                    NetPlanItem item_front = items_map.get(x);

                    item_front.setBacksize(item_front.getBacksize()-1);
                    if(item_front.getBacksize() == 0) {
                        items_queue.offer(item_front);
                    }
                }
            }

            if(!item.getIdsback().isEmpty()) {
                for (Integer x : item.getIdsback()) {
                    NetPlanItem item_back = items_map.get(x);

                    item_back.setLS(item_back.getLF() - item_back.getDP());
                    if(item.getLF() < 0 || item_back.getLS() < item.getLF()) {
                        item.setLF(item_back.getLS()-item_back.getTL());
                        item.setLS(item.getLF()-item.getDP());
                    }
                }
            }
        }

        filterItems(max_level, "ESAsc", false, false, false);
    }
}
