package cc.bukkitPlugin.commons.nmsutil.nbt;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import cc.bukkitPlugin.commons.nmsutil.NMSUtil;
import cc.commons.util.ToolKit;
import cc.commons.util.extra.CList;
import cc.commons.util.reflect.ClassUtil;
import cc.commons.util.reflect.FieldUtil;
import cc.commons.util.reflect.LookupUtil;
import cc.commons.util.reflect.MethodUtil;
import cc.commons.util.reflect.filter.FieldFilter;
import cc.commons.util.reflect.filter.MethodFilter;

public class NBTUtil{

    // NBTTag Type id
    public static final int NBT_End=0;
    public static final int NBT_Byte=1;
    public static final int NBT_Short=2;
    public static final int NBT_Int=3;
    public static final int NBT_Long=4;
    public static final int NBT_Float=5;
    public static final int NBT_Double=6;
    public static final int NBT_Number=99;
    public static final int NBT_ByteArray=7;
    public static final int NBT_String=8;
    public static final int NBT_List=9;
    public static final int NBT_Compound=10;
    public static final int NBT_IntArray=11;

    public static final Class<?> clazz_NBTBase;
    public static final Class<?> clazz_NBTTagEnd;
    public static final Class<?> clazz_NBTTagByte;
    public static final Class<?> clazz_NBTTagShort;
    public static final Class<?> clazz_NBTTagInt;
    public static final Class<?> clazz_NBTTagLong;
    public static final Class<?> clazz_NBTTagFloat;
    public static final Class<?> clazz_NBTTagDouble;
    public static final Class<?> clazz_NBTTagByteArray;
    public static final Class<?> clazz_NBTTagString;
    public static final Class<?> clazz_NBTTagList;
    public static final Class<?> clazz_NBTTagCompound;
    protected static final MethodHandle Constructor_NBTTagCompound;
    public static final Class<?> clazz_NBTTagIntArray;

    public static final Method method_NMSItemStack_getTag;
    protected static final MethodHandle MH_NMSItemStack_getTag;
    public static final Method method_NMSItemStack_setTag;
    protected static final MethodHandle MH_NMSItemStack_setTag;
    public static final Method method_NMSItemStack_saveToNBT;
    protected static final MethodHandle MH_NMSItemStack_saveToNBT;
    public static final Method method_NMSItemStack_loadFromNBT;
    protected static final MethodHandle MH_NMSItemStack_loadFromNBT;

    public static final Method method_NBTBase_getTypeId;
    protected static final MethodHandle MH_NBTBase_getTypeId;
    public static final Method method_NBTBase_copy;
    protected static final MethodHandle MH_NBTBase_copy;
    public static final Method method_NBTTagCompound_isEmpty;
    protected static final MethodHandle MH_NBTTagCompound_isEmpty;
    public static final Method method_NBTTagCompound_hasKeyOfType;
    protected static final MethodHandle MH_NBTTagCompound_hasKeyOfType;
    public static final Method method_NBTTagCompound_get;
    protected static final MethodHandle MH_NBTTagCompound_get;
    public static final Method method_NBTTagCompound_getInt;
    protected static final MethodHandle MH_NBTTagCompound_getInt;
    public static final Method method_NBTTagCompound_getString;
    protected static final MethodHandle MH_NBTTagCompound_getString;
    public static final Method method_NBTTagCompound_getList;
    protected static final MethodHandle MH_NBTTagCompound_getList;
    public static final Method method_NBTTagCompound_set;
    protected static final MethodHandle MH_NBTTagCompound_set;
    public static final Method method_NBTTagList_add;
    protected static final MethodHandle MH_NBTTagList_add;

    public static final Field field_NBTTagString_value;
    protected static final MethodHandle Getter_NBTTagString_value;
    public static final Field field_NBTTagByte_value;
    protected static final MethodHandle Getter_NBTTagByte_value;
    public static final Field field_NBTTagShort_value;
    protected static final MethodHandle Getter_NBTTagShort_value;
    public static final Field field_NBTTagInt_value;
    protected static final MethodHandle Getter_NBTTagInt_value;
    public static final Field field_NBTTagFloat_value;
    protected static final MethodHandle Getter_NBTTagFloat_value;
    public static final Field field_NBTTagDouble_value;
    protected static final MethodHandle Getter_NBTTagDouble_value;
    public static final Field field_NBTTagLong_value;
    protected static final MethodHandle Getter_NBTTagLong_value;
    public static final Field field_NBTTagList_value;
    protected static final MethodHandle Getter_NBTTagList_value;
    public static final Field field_NBTTagList_valueType;
    protected static final MethodHandle Getter_NBTTagList_valueType;
    public static final Field field_NBTTagByteArray_value;
    protected static final MethodHandle Getter_NBTTagByteArray_value;
    public static final Field field_NBTTagIntArray_value;
    protected static final MethodHandle Getter_NBTTagIntArray_value;
    public static final Field field_NBTTagCompound_map;
    protected static final MethodHandle Getter_NBTTagCompound_map;
    public static final Field field_NMSItemStack_tag;
    protected static final MethodHandle Getter_NMSItemStack_tag;
    protected static final MethodHandle Setter_NMSItemStack_tag;

    static{
        Field tNMSItem_Tag=null;
        CList<Method> tMethods=MethodUtil.getDeclaredMethod(NMSUtil.clazz_NMSItemStack,MethodFilter.c()
                .noParam().addFilter((pMethod)->pMethod.getReturnType().getSimpleName().equals("NBTTagCompound")));
        if(tMethods.onlyOne()){
            method_NMSItemStack_getTag=tMethods.oneGet();
        }else{
            int tPos=-1;
            Object tNMSItem=NMSUtil.getNMSItem(new ItemStack(Material.STONE));
            Class<?> tTagClazz=tMethods.first().getReturnType();
            CList<Field> tFields=FieldUtil.getDeclaredField(NMSUtil.clazz_NMSItemStack,FieldFilter.t(tTagClazz));
            for(int i=tFields.size()-1;i>=0;i--){
                Object tTag=ClassUtil.newInstance(tTagClazz);
                FieldUtil.setFieldValue(tFields.get(i),tNMSItem,tTag);

                for(int j=tMethods.size()-1;j>=0;j--){
                    if(MethodUtil.invokeMethod(tMethods.get(j),tNMSItem)==tTag){
                        tPos=j;
                        tNMSItem_Tag=tFields.get(i);
                        i=-1;
                        break;
                    }
                }
            }

            method_NMSItemStack_getTag=tPos!=-1?tMethods.get(tPos):null;
            if(tPos==-1) throw new IllegalStateException("Cann't init nbtutil");
        }
        MH_NMSItemStack_getTag=LookupUtil.unreflect(method_NMSItemStack_getTag);

        clazz_NBTTagCompound=method_NMSItemStack_getTag.getReturnType();
        Constructor_NBTTagCompound=LookupUtil.unreflectConstructor(ClassUtil.getConstrouctor(clazz_NBTTagCompound));
        String tPacketPath=ClassUtil.getClassPacket(clazz_NBTTagCompound.getName());
        clazz_NBTBase=ClassUtil.getClass(tPacketPath+"NBTBase");
        clazz_NBTTagByte=ClassUtil.getClass(tPacketPath+"NBTTagByte");
        clazz_NBTTagShort=ClassUtil.getClass(tPacketPath+"NBTTagShort");
        clazz_NBTTagInt=ClassUtil.getClass(tPacketPath+"NBTTagInt");
        clazz_NBTTagLong=ClassUtil.getClass(tPacketPath+"NBTTagLong");
        clazz_NBTTagFloat=ClassUtil.getClass(tPacketPath+"NBTTagFloat");
        clazz_NBTTagDouble=ClassUtil.getClass(tPacketPath+"NBTTagDouble");
        clazz_NBTTagEnd=ClassUtil.getClass(tPacketPath+"NBTTagEnd");
        clazz_NBTTagByteArray=ClassUtil.getClass(tPacketPath+"NBTTagByteArray");
        clazz_NBTTagString=ClassUtil.getClass(tPacketPath+"NBTTagString");
        clazz_NBTTagIntArray=ClassUtil.getClass(tPacketPath+"NBTTagIntArray");
        clazz_NBTTagList=ClassUtil.getClass(tPacketPath+"NBTTagList");

        method_NBTBase_getTypeId=MethodUtil.getDeclaredMethod(clazz_NBTBase,MethodFilter.rt(byte.class).noParam()).oneGet();
        MH_NBTBase_getTypeId=LookupUtil.unreflect(method_NBTBase_getTypeId);
        method_NBTBase_copy=MethodUtil.getDeclaredMethod(clazz_NBTBase,MethodFilter.rt(clazz_NBTBase).noParam()).oneGet();
        MH_NBTBase_copy=LookupUtil.unreflect(method_NBTBase_copy);
        method_NBTTagCompound_isEmpty=MethodUtil.getDeclaredMethod(clazz_NBTTagCompound,MethodFilter.rt(boolean.class).noParam()).oneGet();
        MH_NBTTagCompound_isEmpty=LookupUtil.unreflect(method_NBTTagCompound_isEmpty);
        method_NBTTagCompound_hasKeyOfType=MethodUtil.getDeclaredMethod(clazz_NBTTagCompound,
                MethodFilter.rpt(boolean.class,String.class,int.class)).oneGet();
        MH_NBTTagCompound_hasKeyOfType=LookupUtil.unreflect(method_NBTTagCompound_hasKeyOfType);
        method_NBTTagCompound_get=MethodUtil.getDeclaredMethod(clazz_NBTTagCompound,MethodFilter.rpt(clazz_NBTBase,String.class)).oneGet();
        MH_NBTTagCompound_get=LookupUtil.unreflect(method_NBTTagCompound_get);
        method_NBTTagCompound_getInt=MethodUtil.getDeclaredMethod(clazz_NBTTagCompound,MethodFilter.rpt(int.class,String.class)).oneGet();
        MH_NBTTagCompound_getInt=LookupUtil.unreflect(method_NBTTagCompound_getInt);
        method_NBTTagCompound_getString=MethodUtil.getDeclaredMethod(clazz_NBTTagCompound,
                MethodFilter.rpt(String.class,String.class).addDeniedModifer(Modifier.STATIC)).oneGet();
        MH_NBTTagCompound_getString=LookupUtil.unreflect(method_NBTTagCompound_getString);
        method_NBTTagCompound_getList=MethodUtil.getDeclaredMethod(clazz_NBTTagCompound,
                MethodFilter.rpt(clazz_NBTTagList,String.class,int.class)).oneGet();
        MH_NBTTagCompound_getList=LookupUtil.unreflect(method_NBTTagCompound_getList);
        method_NBTTagCompound_set=MethodUtil.getDeclaredMethod(clazz_NBTTagCompound,
                MethodFilter.rpt(void.class,String.class,clazz_NBTBase)).oneGet();
        MH_NBTTagCompound_set=LookupUtil.unreflect(method_NBTTagCompound_set);
        if(MethodUtil.isDeclaredMethodExist(clazz_NBTTagList,MethodFilter.rpt(void.class,clazz_NBTBase))){
            method_NBTTagList_add=MethodUtil.getDeclaredMethod(clazz_NBTTagList,MethodFilter.rpt(void.class,clazz_NBTBase)).oneGet();
        }else{
            // 1.13+
            method_NBTTagList_add=MethodUtil.getDeclaredMethod(clazz_NBTTagList,MethodFilter.rpt(boolean.class,clazz_NBTBase)).oneGet();
        }
        MH_NBTTagList_add=LookupUtil.unreflect(method_NBTTagList_add);

        field_NBTTagByte_value=FieldUtil.getDeclaredField(clazz_NBTTagByte,FieldFilter.t(byte.class)).oneGet();
        Getter_NBTTagByte_value=LookupUtil.unreflectGetter(field_NBTTagByte_value);
        field_NBTTagShort_value=FieldUtil.getDeclaredField(clazz_NBTTagShort,FieldFilter.t(short.class)).oneGet();
        Getter_NBTTagShort_value=LookupUtil.unreflectGetter(field_NBTTagShort_value);
        field_NBTTagInt_value=FieldUtil.getDeclaredField(clazz_NBTTagInt,FieldFilter.t(int.class)).oneGet();
        Getter_NBTTagInt_value=LookupUtil.unreflectGetter(field_NBTTagInt_value);
        field_NBTTagFloat_value=FieldUtil.getDeclaredField(clazz_NBTTagFloat,FieldFilter.t(float.class)).oneGet();
        Getter_NBTTagFloat_value=LookupUtil.unreflectGetter(field_NBTTagFloat_value);
        field_NBTTagDouble_value=FieldUtil.getDeclaredField(clazz_NBTTagDouble,FieldFilter.t(double.class)).oneGet();
        Getter_NBTTagDouble_value=LookupUtil.unreflectGetter(field_NBTTagDouble_value);
        field_NBTTagLong_value=FieldUtil.getDeclaredField(clazz_NBTTagLong,FieldFilter.t(long.class)).oneGet();
        Getter_NBTTagLong_value=LookupUtil.unreflectGetter(field_NBTTagLong_value);
        field_NBTTagString_value=FieldUtil.getDeclaredField(clazz_NBTTagString,
                FieldFilter.t(String.class).addPossModifer(Modifier.PRIVATE).addDeniedModifer(Modifier.STATIC)).oneGet();
        Getter_NBTTagString_value=LookupUtil.unreflectGetter(field_NBTTagString_value);
        field_NBTTagList_value=FieldUtil.getDeclaredField(clazz_NBTTagList,FieldFilter.t(List.class)).oneGet();
        Getter_NBTTagList_value=LookupUtil.unreflectGetter(field_NBTTagList_value);
        field_NBTTagList_valueType=FieldUtil.getDeclaredField(clazz_NBTTagList,FieldFilter.t(byte.class)).oneGet();
        Getter_NBTTagList_valueType=LookupUtil.unreflectGetter(field_NBTTagList_valueType);
        field_NBTTagByteArray_value=FieldUtil.getDeclaredField(clazz_NBTTagByteArray,FieldFilter.t(byte[].class)).oneGet();
        Getter_NBTTagByteArray_value=LookupUtil.unreflectGetter(field_NBTTagByteArray_value);
        field_NBTTagIntArray_value=FieldUtil.getDeclaredField(clazz_NBTTagIntArray,FieldFilter.t(int[].class)).oneGet();
        Getter_NBTTagIntArray_value=LookupUtil.unreflectGetter(field_NBTTagIntArray_value);
        field_NBTTagCompound_map=FieldUtil.getDeclaredField(clazz_NBTTagCompound,FieldFilter.t(Map.class)).oneGet();
        Getter_NBTTagCompound_map=LookupUtil.unreflectGetter(field_NBTTagCompound_map);
        field_NMSItemStack_tag=tNMSItem_Tag!=null?tNMSItem_Tag
                :FieldUtil.getDeclaredField(NMSUtil.clazz_NMSItemStack,FieldFilter.t(clazz_NBTTagCompound)).oneGet();
        Getter_NMSItemStack_tag=LookupUtil.unreflectGetter(field_NMSItemStack_tag);
        Setter_NMSItemStack_tag=LookupUtil.unreflectSetter(field_NMSItemStack_tag);
        // ItemStack
        method_NMSItemStack_saveToNBT=MethodUtil.getDeclaredMethod(NMSUtil.clazz_NMSItemStack,MethodFilter.rpt(clazz_NBTTagCompound,clazz_NBTTagCompound)).oneGet();
        MH_NMSItemStack_saveToNBT=LookupUtil.unreflect(method_NMSItemStack_saveToNBT);
        tMethods=MethodUtil.getDeclaredMethod(NMSUtil.clazz_NMSItemStack,MethodFilter.rpt(void.class,clazz_NBTTagCompound));
        int setTagMethodIndex=0;
        Object tTag=ClassUtil.newInstance(clazz_NBTTagCompound);
        Object tNMSItem=NMSUtil.getNMSItem(new ItemStack(Material.STONE,1,(short)0));
        MethodUtil.invokeMethod(tMethods.get(setTagMethodIndex),tNMSItem,tTag);
        if(FieldUtil.getFieldValue(field_NMSItemStack_tag,tNMSItem)!=tTag){
            setTagMethodIndex=1;
        }
        method_NMSItemStack_setTag=tMethods.get(setTagMethodIndex);
        MH_NMSItemStack_setTag=LookupUtil.unreflect(method_NMSItemStack_setTag);
        method_NMSItemStack_loadFromNBT=tMethods.get(1-setTagMethodIndex);
        MH_NMSItemStack_loadFromNBT=LookupUtil.unreflect(method_NMSItemStack_loadFromNBT);
    }

    /**
     * 获取物品的NBT
     * 
     * @param pItem
     *            Bukkit物品
     * @return 物品NBT,非null
     */
    public static Object getItemNBT(ItemStack pItem){
        return NBTUtil.getItemNBT_NMS(NMSUtil.getNMSItem(pItem));
    }

    /**
     * 获取物品的NBT
     * 
     * @param pNMSItem
     *            NMS物品
     * @return 物品NBT,非null
     */
    public static Object getItemNBT_NMS(Object pNMSItem){
        if(pNMSItem==null)
            return NBTUtil.newNBTTagCompound();

        Object tTag;
        try{
            tTag=Getter_NMSItemStack_tag.invoke(pNMSItem);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
        if(tTag==null){
            tTag=NBTUtil.newNBTTagCompound();
            NBTUtil.setItemNBT_NMS(pNMSItem,tTag);
        }
        return tTag;
    }

    /**
     * 设置物品的NBT
     * 
     * @param pItem
     *            Bukkit物品
     * @param pNBTTag
     *            NBT
     * @return 设置了NBT的物品,可能为原物品
     */
    public static ItemStack setItemNBT(ItemStack pItem,Object pNBTTag){
        Object tNMSItem=NMSUtil.getItemHandle(pItem);
        if(tNMSItem!=null){
            NBTUtil.setItemNBT_NMS(tNMSItem,pNBTTag);
        }else{
            tNMSItem=NMSUtil.asNMSItemCopy(pItem);
            NBTUtil.setItemNBT_NMS(tNMSItem,pNBTTag);
            pItem=NMSUtil.getCBTItem(tNMSItem);
        }

        return pItem;
    }

    /**
     * 设置物品的NBT
     * 
     * @param pNMSItem
     *            NMS物品
     * @param pNBTTag
     *            NBT
     */
    public static void setItemNBT_NMS(Object pNMSItem,Object pNBTTag){
        if(pNMSItem==null) return;

        try{
            Setter_NMSItemStack_tag.invoke(pNMSItem,pNBTTag);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * 保存物品数据到NBT
     * 
     * @param pItem
     *            Bukkit物品
     * @return NBT数据
     */
    public static Object saveItemToNBT(ItemStack pItem){
        return NBTUtil.saveItemToNBT_NMS(NMSUtil.getNMSItem(pItem));
    }

    /**
     * 保存物品数据到NBT
     * 
     * @param pNMSItem
     *            NMS物品
     * @return NBT数据
     */
    public static Object saveItemToNBT_NMS(Object pNMSItem){
        Object tTag=NBTUtil.newNBTTagCompound();
        if(pNMSItem==null) return tTag;

        try{
            MH_NMSItemStack_saveToNBT.invoke(pNMSItem,tTag);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
        return tTag;
    }

    /**
     * 获取NBT的值
     * <p>
     * 值中不包含类型后缀<br>
     * 例如原NBTTagShort会表示为2s,但是此函数中只会表示为2
     * </p>
     * 
     * @param pNBTBase
     * @return 类Json字符串
     */
    public static String getNBTBaseValueWithoTypeSuffix(Object pNBTBase){
        if(pNBTBase==null)
            return "";

        if(NBTUtil.clazz_NBTTagEnd.isInstance(pNBTBase)){
            return "";
        }else if(NBTUtil.clazz_NBTTagByte.isInstance(pNBTBase)){
            return String.valueOf(NBTUtil.getNBTTagByteValue(pNBTBase));
        }else if(NBTUtil.clazz_NBTTagShort.isInstance(pNBTBase)){
            return String.valueOf(NBTUtil.getNBTTagShortValue(pNBTBase));
        }else if(NBTUtil.clazz_NBTTagInt.isInstance(pNBTBase)){
            return String.valueOf(NBTUtil.getNBTTagIntValue(pNBTBase));
        }else if(NBTUtil.clazz_NBTTagLong.isInstance(pNBTBase)){
            return String.valueOf(NBTUtil.getNBTTagLongValue(pNBTBase));
        }else if(NBTUtil.clazz_NBTTagFloat.isInstance(pNBTBase)){
            return String.valueOf(NBTUtil.getNBTTagFloatValue(pNBTBase));
        }else if(NBTUtil.clazz_NBTTagDouble.isInstance(pNBTBase)){
            return String.valueOf(NBTUtil.getNBTTagDoubleValue(pNBTBase));
        }else if(NBTUtil.clazz_NBTTagByteArray.isInstance(pNBTBase)){
            byte[] bvals=NBTUtil.getNBTTagByteArrayValue(pNBTBase);
            StringBuilder tSB=new StringBuilder("[");
            for(byte sValue : bvals){
                tSB.append(sValue).append(',');
            }
            if(bvals.length>0)
                tSB.delete(tSB.length()-1,tSB.length()-1);
            return tSB.append(']').toString();
        }else if(NBTUtil.clazz_NBTTagString.isInstance(pNBTBase)){
            return NBTUtil.getNBTTagStringValue(pNBTBase);
        }else if(NBTUtil.clazz_NBTTagIntArray.isInstance(pNBTBase)){
            int[] ivals=NBTUtil.getNBTTagIntArrayValue(pNBTBase);
            StringBuilder tSB=new StringBuilder("[");
            for(int sValue : ivals){
                tSB.append(sValue).append(',');
            }
            if(ivals.length>0)
                tSB.delete(tSB.length()-1,tSB.length()-1);
            return tSB.append(']').toString();
        }else if(NBTUtil.clazz_NBTTagList.isInstance(pNBTBase)){
            StringBuilder tSB=new StringBuilder("[");
            List<Object> tContent=NBTUtil.getNBTTagListValue(pNBTBase);
            for(Object sNBTBase : tContent){
                tSB.append(NBTUtil.getNBTBaseValueWithoTypeSuffix(sNBTBase)).append(',');
            }
            if(tContent.size()>0)
                tSB.delete(tSB.length()-1,tSB.length());
            return tSB.append(']').toString();
        }else if(NBTUtil.clazz_NBTTagCompound.isInstance(pNBTBase)){
            StringBuilder tSB=new StringBuilder("{");
            Map<String,Object> tContent=NBTUtil.getNBTTagCompoundValue(pNBTBase);
            for(Map.Entry<String,Object> sEntry : tContent.entrySet()){
                tSB.append(sEntry.getKey()).append(':').append(NBTUtil.getNBTBaseValueWithoTypeSuffix(sEntry.getValue())).append(',');
            }
            if(tContent.size()>0)
                tSB.delete(tSB.length()-1,tSB.length());
            return tSB.append('}').toString();
        }else{
            return String.valueOf(pNBTBase);
        }
    }

    /**
     * 获取物品的NBT的的值域Map
     *
     * @param pItem
     *            物品,可以为null
     * @return 可能,非null
     */
    public static Map<String,Object> getNBTTagCompoundValueFromItem(ItemStack pItem){
        return NBTUtil.getNBTTagCompoundValue(NBTUtil.getItemNBT(pItem));
    }

    /**
     * 获取NBTTag的NBT类型
     * 
     * @param pNBTTag
     *            NBTBase实例
     * @return 类型
     */
    @SuppressWarnings("unchecked")
    public static byte getNBTTagTypeId(Object pNBTTag){
        try{
            return (byte)MH_NBTBase_getTypeId.invoke(pNBTTag);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * 根据NBT的类型id获取NBT类型
     * <p>
     * 注意,不判定{@link #NBT_Number}为一种类型
     * </p>
     * 
     * @param pTypeId
     *            类型id
     * @return NBT的类型,如果未找到,返回null
     */
    public static Class<?> getNBTTypeById(int pTypeId){
        switch(pTypeId){
            case NBT_End:
                return clazz_NBTTagEnd;
            case NBT_Byte:
                return clazz_NBTTagByte;
            case NBT_Short:
                return clazz_NBTTagShort;
            case NBT_Int:
                return clazz_NBTTagInt;
            case NBT_Long:
                return clazz_NBTTagLong;
            case NBT_Float:
                return clazz_NBTTagFloat;
            case NBT_Double:
                return clazz_NBTTagDouble;
            case NBT_ByteArray:
                return clazz_NBTTagByteArray;
            case NBT_String:
                return clazz_NBTTagString;
            case NBT_List:
                return clazz_NBTTagList;
            case NBT_Compound:
                return clazz_NBTTagCompound;
            case NBT_IntArray:
                return clazz_NBTTagIntArray;
            default:
                return null;
        }
    }

    public static Object invokeNBTTagCopy(Object pNBTTag){
        try{
            return MH_NBTBase_copy.invoke(pNBTTag);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagEnd(){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagEnd);
    }

    public static boolean isNBTTagEnd(Object pObj){
        return NBTUtil.clazz_NBTTagEnd.isInstance(pObj);
    }

    public static Object newNBTTagByte(byte pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagByte,byte.class,pValue);
    }

    public static boolean isNBTTagByte(Object pObj){
        return NBTUtil.clazz_NBTTagByte.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static byte getNBTTagByteValue(Object pNBTTagByte){
        try{
            return (byte)Getter_NBTTagByte_value.invoke(pNBTTagByte);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagShort(short pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagShort,short.class,pValue);
    }

    public static boolean isNBTTagShort(Object pObj){
        return NBTUtil.clazz_NBTTagShort.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static short getNBTTagShortValue(Object pNBTTagShort){
        try{
            return (short)Getter_NBTTagShort_value.invoke(pNBTTagShort);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagInt(int pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagInt,int.class,pValue);
    }

    public static boolean isNBTTagInt(Object pObj){
        return NBTUtil.clazz_NBTTagInt.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static int getNBTTagIntValue(Object pNBTTagInt){
        try{
            return (int)Getter_NBTTagInt_value.invoke(pNBTTagInt);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagLong(long pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagLong,long.class,pValue);
    }

    public static boolean isNBTTagLong(Object pObj){
        return NBTUtil.clazz_NBTTagLong.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static long getNBTTagLongValue(Object pNBTTagLong){
        try{
            return (long)Getter_NBTTagLong_value.invoke(pNBTTagLong);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagFloat(float pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagFloat,float.class,pValue);
    }

    public static boolean isNBTTagFloat(Object pObj){
        return NBTUtil.clazz_NBTTagFloat.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static float getNBTTagFloatValue(Object pNBTTagFloat){
        try{
            return (float)Getter_NBTTagFloat_value.invoke(pNBTTagFloat);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagDouble(double pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagDouble,double.class,pValue);
    }

    public static boolean isNBTTagDouble(Object pObj){
        return NBTUtil.clazz_NBTTagDouble.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static double getNBTTagDoubleValue(Object pNBTTagDouble){
        try{
            return (double)Getter_NBTTagDouble_value.invoke(pNBTTagDouble);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagByteArray(byte[] pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagByteArray,byte[].class,pValue);
    }

    public static boolean isNBTTagByteArray(Object pObj){
        return NBTUtil.clazz_NBTTagByteArray.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static byte[] getNBTTagByteArrayValue(Object pNBTTagByteArray){
        try{
            return (byte[])Getter_NBTTagByteArray_value.invoke(pNBTTagByteArray);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagString(String pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagString,String.class,pValue);
    }

    public static boolean isNBTTagString(Object pObj){
        return NBTUtil.clazz_NBTTagString.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static String getNBTTagStringValue(Object pNBTTagString){
        try{
            return (String)Getter_NBTTagString_value.invoke(pNBTTagString);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagList(){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagList);
    }

    public static boolean isNBTTagList(Object pObj){
        return NBTUtil.clazz_NBTTagList.isInstance(pObj);
    }

    /**
     * 获取类型为NBTTagList的内部List值
     * 
     * @param pNBTTagList
     *            实例,类型必须为NBTTagList,可以为null
     * @return List,非null
     */
    @SuppressWarnings("unchecked")
    public static List<Object> getNBTTagListValue(Object pNBTTagList){
        if(pNBTTagList==null){
            return new ArrayList<>(0);
        }

        try{
            return (List<Object>)Getter_NBTTagList_value.invoke(pNBTTagList);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * 添加一个NBT值到NBTTagList实例中
     * 
     * @param pNBTTagList
     *            添加到的TagList
     * @param pNBTBase
     *            要添加的内容,必须是NBTBase的实例
     */
    public static void invokeNBTTagList_add(Object pNBTTagList,Object pNBTBase){
        try{
            MH_NBTTagList_add.invoke(pNBTBase);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Class<?> getNBTTagListValueType(Object pNBTList){
        List<Object> tListValue=NBTUtil.getNBTTagListValue(pNBTList);
        if(!tListValue.isEmpty()){
            return tListValue.get(0).getClass();
        }else{
            int tId;
            try{
                tId=(byte)Getter_NBTTagList_valueType.invoke(pNBTList);
            }catch(Throwable e){
                throw new IllegalStateException(e);
            }
            return tId==0?null:NBTUtil.getNBTTypeById(tId);
        }
    }

    /**
     * 获取一个新的NBTTagCompound实例
     * 
     * @return 实例
     */
    public static Object newNBTTagCompound(){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagCompound);
    }

    public static boolean isNBTTagCompound(Object pObj){
        return NBTUtil.clazz_NBTTagCompound.isInstance(pObj);
    }

    /**
     * 获取类类型为NBTTagCompound的值域Map
     *
     * @param pNBTTagCompound
     *            实例,类型必须为NBTTagCompound,可以为null
     * @return Map,非null
     */
    @SuppressWarnings("unchecked")
    public static Map<String,Object> getNBTTagCompoundValue(Object pNBTTagCompound){
        if(!NBTUtil.isNBTTagCompound(pNBTTagCompound)){
            return new HashMap<>(0);
        }

        try{
            return (Map<String,Object>)Getter_NBTTagCompound_map.invoke(pNBTTagCompound);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取NBTTagCompound中的元素
     * 
     * @param pNBTTagCompound
     *            NBTTagCompound实例,允许为null
     * @param pKey
     *            键
     * @return 值或null
     */
    public static Object invokeNBTTagCompound_get(Object pNBTTagCompound,String pKey){
        if(pNBTTagCompound==null) return null;
        try{
            return MH_NBTTagCompound_get.invoke(pNBTTagCompound,pKey);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static String invokeNBTTagCompound_getString(Object pNBTTagCompound,String pKey){
        if(pNBTTagCompound==null) return null;
        try{
            return (String)MH_NBTTagCompound_getString.invoke(pNBTTagCompound,pKey);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static int invokeNBTTagCompound_getInt(Object pNBTTagCompound,String pKey){
        if(pNBTTagCompound==null) return 0;
        try{
            return (int)MH_NBTTagCompound_getInt.invoke(pNBTTagCompound,pKey);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取NBTTagCompound中的NBTTagList元素
     * 
     * @param pNBTTagCompound
     *            NBTTagCompound实例,允许为null
     * @param pKey
     *            键
     * @param pNBTType
     *            NBTTagList 所包含的NBT类型
     * @return NBTTagList实例或null
     */
    public static Object invokeNBTTagCompound_getList(Object pNBTTagCompound,String pKey,int pNBTType){
        if(pNBTTagCompound==null) return null;
        try{
            return MH_NBTTagCompound_getList.invoke(pNBTTagCompound,pKey,pNBTType);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * 设置NBTTagCompound的元素
     * 
     * @param pNBTTagCompound
     *            NBTTagCompound实例,不能null
     * @param pKey
     *            键
     * @param pNBTBase
     *            NBTBase实例
     */
    public static void invokeNBTTagCompound_set(Object pNBTTagCompound,String pKey,Object pNBTBase){
        if(pNBTTagCompound==null) return;
        try{
            MH_NBTTagCompound_set.invoke(pNBTTagCompound,pKey,pNBTBase);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * 从NBTTagCompound实例中移除指定的标签
     *
     * @param pNBTTagCompound
     *            NBTTagCompound实例,允许为null
     * @param pKey
     *            NBT标签
     * @return 移除的值或null
     */
    public static Object invokeNBTTagCompound_remove(Object pNBTTagCompound,String pKey){
        Map<String,Object> tValue=NBTUtil.getNBTTagCompoundValue(pNBTTagCompound);
        return tValue==null?null:tValue.remove(pKey);
    }

    /**
     * 克隆NBTTagCompound
     * 
     * @param pNBTTagCompound
     *            NBTTagCompound实例,允许为null
     * @return 克隆的NBTTagCompound,或新的空NBTTagCompound
     */
    public static Object invokeNBTTagCompound_clone(Object pNBTTagCompound){
        try{
            return pNBTTagCompound==null?NBTUtil.newNBTTagCompound():MH_NBTBase_copy.invoke(pNBTTagCompound);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    public static Object newNBTTagIntArray(int[] pValue){
        return ClassUtil.newInstance(NBTUtil.clazz_NBTTagIntArray,int[].class,pValue);
    }

    public static boolean isNBTTagIntArray(Object pObj){
        return NBTUtil.clazz_NBTTagIntArray.isInstance(pObj);
    }

    @SuppressWarnings("unchecked")
    public static int[] getNBTTagIntArrayValue(Object pNBTTagIntArray){
        try{
            return (int[])Getter_NBTTagIntArray_value.invoke(pNBTTagIntArray);
        }catch(Throwable e){
            throw new IllegalStateException(e);
        }
    }

    private static boolean EMPTY_NBTSTRING=ToolKit.compareVersion(NMSUtil.getMinecraftVersion(),"1.13")>=0;

    /**
     * 混合两个NBTTagCompound,并将混合结果放置打新的NBTTagCompound中
     * <p>
     * 注意,混合过程中不会更改两个参数中的数据,新数据会另外写入新的NBTTagCompound,因此,请务必接收函数的返回结果<br>
     * NBT同名Key替换策略,如果类型相同且是List或者Compound类型,将会进行递归替换,其他情况依据参数pRelaceDes保留前者还是后者
     * </p>
     * 
     * @param pNBTTagDes
     *            目标NBT数据,NBTTagCompound实例
     * @param pNBTTagSrc
     *            来源NBT数据,NBTTagCompound实例
     * @param pRelaceDes
     *            如果存在同名NBT数据时,是否使用Src中的数据替换Des
     * @return
     */
    public static Object mixNBT(Object pNBTTagDes,Object pNBTTagSrc,boolean pRelaceDes){
        Object tNewTag=NBTUtil.newNBTTagCompound();
        Map<String,Object> tMixMapValue=NBTUtil.getNBTTagCompoundValue(tNewTag);
        Map<String,Object> tDesMapValue=NBTUtil.getNBTTagCompoundValue(pNBTTagDes);
        Map<String,Object> tSrcMapValue=NBTUtil.getNBTTagCompoundValue(pNBTTagSrc);
        HashSet<String> tKeys=new HashSet<>(tDesMapValue.keySet());
        tKeys.addAll(tSrcMapValue.keySet());
        for(String sKey : tKeys){
            Object tDesEle=tDesMapValue.get(sKey);
            Object tSrcEle=tSrcMapValue.get(sKey);
            if(tSrcEle==null&&tDesEle!=null){
                tMixMapValue.put(sKey,NBTUtil.invokeNBTTagCopy(tDesEle));
            }else if(tDesEle==null&&tSrcEle!=null){
                tMixMapValue.put(sKey,NBTUtil.invokeNBTTagCopy(tSrcEle));
            }else if(tDesEle!=null&&tSrcEle!=null){
                if(tSrcEle.getClass()==tDesEle.getClass()){
                    if(NBTUtil.isNBTTagCompound(tSrcEle)){
                        tMixMapValue.put(sKey,NBTUtil.mixNBT(tDesEle,tSrcEle,pRelaceDes));
                        continue;
                    }else if(NBTUtil.isNBTTagList(tSrcEle)){
                        Object tStoreTarget;
                        if(NBTUtil.getNBTTagListValue(tDesEle).isEmpty()){
                            tStoreTarget=tSrcEle;
                        }else if(NBTUtil.getNBTTagListValue(tSrcEle).isEmpty()){
                            tStoreTarget=tDesEle;
                        }else{
                            tStoreTarget=pRelaceDes?tSrcEle:tDesEle;
                        }
                        tMixMapValue.put(sKey,NBTUtil.invokeNBTTagCopy(tStoreTarget));
                        continue;
                    }
                }
                Object tStoreTarget=null;
                if(NBTUtil.isNBTTagString(tSrcEle)&&tSrcEle.toString().equals("\"minecraft:empty\"")){
                    tStoreTarget=tDesEle;
                }else if(NBTUtil.isNBTTagString(tDesEle)&&tDesEle.toString().equals("\"minecraft:empty\"")){
                    tStoreTarget=tSrcEle;
                }else{
                    tStoreTarget=pRelaceDes?tSrcEle:tDesEle;
                }
                tMixMapValue.put(sKey,NBTUtil.invokeNBTTagCopy(tStoreTarget));
            }
        }
        return tNewTag;
    }

}
