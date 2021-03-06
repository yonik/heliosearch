package org.apache.solr.search.function.funcvalues;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.solr.search.function.FuncValues;
import org.apache.solr.search.function.ValueSource;
import org.apache.solr.search.mutable.MutableValue;
import org.apache.solr.search.mutable.MutableValueBool;

/**
 * Abstract {@link org.apache.solr.search.function.FuncValues} implementation which supports retrieving boolean values.
 * Implementations can control how the boolean values are loaded through {@link #boolVal(int)}}
 */
public abstract class BoolFuncValues extends FuncValues {
  protected final ValueSource vs;

  public BoolFuncValues(ValueSource vs) {
    this.vs = vs;
  }

  @Override
  public abstract boolean boolVal(int doc);

  @Override
  public byte byteVal(int doc) {
    return boolVal(doc) ? (byte) 1 : (byte) 0;
  }

  @Override
  public short shortVal(int doc) {
    return boolVal(doc) ? (short) 1 : (short) 0;
  }

  @Override
  public float floatVal(int doc) {
    return boolVal(doc) ? (float) 1 : (float) 0;
  }

  @Override
  public int intVal(int doc) {
    return boolVal(doc) ? 1 : 0;
  }

  @Override
  public long longVal(int doc) {
    return boolVal(doc) ? (long) 1 : (long) 0;
  }

  @Override
  public double doubleVal(int doc) {
    return boolVal(doc) ? (double) 1 : (double) 0;
  }

  @Override
  public String strVal(int doc) {
    return Boolean.toString(boolVal(doc));
  }

  @Override
  public Object objectVal(int doc) {
    return exists(doc) ? boolVal(doc) : null;
  }

  @Override
  public String toString(int doc) {
    return vs.description() + '=' + strVal(doc);
  }

  @Override
  public ValueFiller getValueFiller() {
    return new ValueFiller() {
      private final MutableValueBool mval = new MutableValueBool();

      @Override
      public MutableValue getValue() {
        return mval;
      }

      @Override
      public void fillValue(int doc) {
        mval.value = boolVal(doc);
        mval.exists = exists(doc);
      }
    };
  }
}
