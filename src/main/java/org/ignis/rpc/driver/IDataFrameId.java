/**
 * Autogenerated by Thrift Compiler (0.14.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.ignis.rpc.driver;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class IDataFrameId implements org.apache.thrift.TBase<IDataFrameId, IDataFrameId._Fields>, java.io.Serializable, Cloneable, Comparable<IDataFrameId> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IDataFrameId");

  private static final org.apache.thrift.protocol.TField CLUSTER_FIELD_DESC = new org.apache.thrift.protocol.TField("cluster", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField WORKER_FIELD_DESC = new org.apache.thrift.protocol.TField("worker", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField DATA_FRAME_FIELD_DESC = new org.apache.thrift.protocol.TField("dataFrame", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new IDataFrameIdStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new IDataFrameIdTupleSchemeFactory();

  private long cluster; // required
  private long worker; // required
  private long dataFrame; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CLUSTER((short)1, "cluster"),
    WORKER((short)2, "worker"),
    DATA_FRAME((short)3, "dataFrame");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // CLUSTER
          return CLUSTER;
        case 2: // WORKER
          return WORKER;
        case 3: // DATA_FRAME
          return DATA_FRAME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __CLUSTER_ISSET_ID = 0;
  private static final int __WORKER_ISSET_ID = 1;
  private static final int __DATAFRAME_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLUSTER, new org.apache.thrift.meta_data.FieldMetaData("cluster", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.WORKER, new org.apache.thrift.meta_data.FieldMetaData("worker", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DATA_FRAME, new org.apache.thrift.meta_data.FieldMetaData("dataFrame", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IDataFrameId.class, metaDataMap);
  }

  public IDataFrameId() {
  }

  public IDataFrameId(
    long cluster,
    long worker,
    long dataFrame)
  {
    this();
    this.cluster = cluster;
    setClusterIsSet(true);
    this.worker = worker;
    setWorkerIsSet(true);
    this.dataFrame = dataFrame;
    setDataFrameIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IDataFrameId(IDataFrameId other) {
    __isset_bitfield = other.__isset_bitfield;
    this.cluster = other.cluster;
    this.worker = other.worker;
    this.dataFrame = other.dataFrame;
  }

  public IDataFrameId deepCopy() {
    return new IDataFrameId(this);
  }

  @Override
  public void clear() {
    setClusterIsSet(false);
    this.cluster = 0;
    setWorkerIsSet(false);
    this.worker = 0;
    setDataFrameIsSet(false);
    this.dataFrame = 0;
  }

  public long getCluster() {
    return this.cluster;
  }

  public IDataFrameId setCluster(long cluster) {
    this.cluster = cluster;
    setClusterIsSet(true);
    return this;
  }

  public void unsetCluster() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CLUSTER_ISSET_ID);
  }

  /** Returns true if field cluster is set (has been assigned a value) and false otherwise */
  public boolean isSetCluster() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CLUSTER_ISSET_ID);
  }

  public void setClusterIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CLUSTER_ISSET_ID, value);
  }

  public long getWorker() {
    return this.worker;
  }

  public IDataFrameId setWorker(long worker) {
    this.worker = worker;
    setWorkerIsSet(true);
    return this;
  }

  public void unsetWorker() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WORKER_ISSET_ID);
  }

  /** Returns true if field worker is set (has been assigned a value) and false otherwise */
  public boolean isSetWorker() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WORKER_ISSET_ID);
  }

  public void setWorkerIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WORKER_ISSET_ID, value);
  }

  public long getDataFrame() {
    return this.dataFrame;
  }

  public IDataFrameId setDataFrame(long dataFrame) {
    this.dataFrame = dataFrame;
    setDataFrameIsSet(true);
    return this;
  }

  public void unsetDataFrame() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DATAFRAME_ISSET_ID);
  }

  /** Returns true if field dataFrame is set (has been assigned a value) and false otherwise */
  public boolean isSetDataFrame() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DATAFRAME_ISSET_ID);
  }

  public void setDataFrameIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DATAFRAME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case CLUSTER:
      if (value == null) {
        unsetCluster();
      } else {
        setCluster((java.lang.Long)value);
      }
      break;

    case WORKER:
      if (value == null) {
        unsetWorker();
      } else {
        setWorker((java.lang.Long)value);
      }
      break;

    case DATA_FRAME:
      if (value == null) {
        unsetDataFrame();
      } else {
        setDataFrame((java.lang.Long)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case CLUSTER:
      return getCluster();

    case WORKER:
      return getWorker();

    case DATA_FRAME:
      return getDataFrame();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case CLUSTER:
      return isSetCluster();
    case WORKER:
      return isSetWorker();
    case DATA_FRAME:
      return isSetDataFrame();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof IDataFrameId)
      return this.equals((IDataFrameId)that);
    return false;
  }

  public boolean equals(IDataFrameId that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_cluster = true;
    boolean that_present_cluster = true;
    if (this_present_cluster || that_present_cluster) {
      if (!(this_present_cluster && that_present_cluster))
        return false;
      if (this.cluster != that.cluster)
        return false;
    }

    boolean this_present_worker = true;
    boolean that_present_worker = true;
    if (this_present_worker || that_present_worker) {
      if (!(this_present_worker && that_present_worker))
        return false;
      if (this.worker != that.worker)
        return false;
    }

    boolean this_present_dataFrame = true;
    boolean that_present_dataFrame = true;
    if (this_present_dataFrame || that_present_dataFrame) {
      if (!(this_present_dataFrame && that_present_dataFrame))
        return false;
      if (this.dataFrame != that.dataFrame)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(cluster);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(worker);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(dataFrame);

    return hashCode;
  }

  @Override
  public int compareTo(IDataFrameId other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetCluster(), other.isSetCluster());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCluster()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cluster, other.cluster);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetWorker(), other.isSetWorker());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWorker()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.worker, other.worker);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetDataFrame(), other.isSetDataFrame());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataFrame()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dataFrame, other.dataFrame);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("IDataFrameId(");
    boolean first = true;

    sb.append("cluster:");
    sb.append(this.cluster);
    first = false;
    if (!first) sb.append(", ");
    sb.append("worker:");
    sb.append(this.worker);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dataFrame:");
    sb.append(this.dataFrame);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'cluster' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'worker' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'dataFrame' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class IDataFrameIdStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public IDataFrameIdStandardScheme getScheme() {
      return new IDataFrameIdStandardScheme();
    }
  }

  private static class IDataFrameIdStandardScheme extends org.apache.thrift.scheme.StandardScheme<IDataFrameId> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, IDataFrameId struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CLUSTER
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.cluster = iprot.readI64();
              struct.setClusterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // WORKER
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.worker = iprot.readI64();
              struct.setWorkerIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DATA_FRAME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.dataFrame = iprot.readI64();
              struct.setDataFrameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetCluster()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'cluster' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetWorker()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'worker' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetDataFrame()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'dataFrame' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, IDataFrameId struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CLUSTER_FIELD_DESC);
      oprot.writeI64(struct.cluster);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(WORKER_FIELD_DESC);
      oprot.writeI64(struct.worker);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DATA_FRAME_FIELD_DESC);
      oprot.writeI64(struct.dataFrame);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IDataFrameIdTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public IDataFrameIdTupleScheme getScheme() {
      return new IDataFrameIdTupleScheme();
    }
  }

  private static class IDataFrameIdTupleScheme extends org.apache.thrift.scheme.TupleScheme<IDataFrameId> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IDataFrameId struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI64(struct.cluster);
      oprot.writeI64(struct.worker);
      oprot.writeI64(struct.dataFrame);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IDataFrameId struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.cluster = iprot.readI64();
      struct.setClusterIsSet(true);
      struct.worker = iprot.readI64();
      struct.setWorkerIsSet(true);
      struct.dataFrame = iprot.readI64();
      struct.setDataFrameIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

