/*
 * Copyright Summit58 LLC and/or licensed to Summit58 LLC
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Summit58 licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.openworkflow.spin.impl.test;

import co.openworkflow.commons.logging.BaseLogger;
import co.openworkflow.spin.SpinRuntimeException;
import co.openworkflow.spin.SpinScriptException;
import co.openworkflow.spin.impl.logging.SpinLogger;

/**
 * Logger for test cases.
 *
 * @author Sebastian Menski
 */
public class SpinTestLogger extends SpinLogger {

  public static final SpinTestLogger TEST_LOGGER = BaseLogger.createLogger(SpinTestLogger.class, PROJECT_CODE, "co.openworkflow.spin.test", "02");

  public void scriptEngineFoundForLanguage(String scriptLanguage) {
    logInfo("001", "Script engine found for script language '{}'", scriptLanguage);
  }

  public void scriptLoaded(String scriptPath) {
    logInfo("002", "Script loaded with filename '{}'", scriptPath);
  }

  public void scriptVariableFound(String name, String type, String value) {
    logInfo("003", "Script variable found with name '{}', type '{}' and value '{}'", name, type, value);
  }

  public void noScriptExtensionFoundForScriptLanguage(String languageName) {
    logWarn("004", "No script extension found for script language '{}'", languageName);
  }

  public void testDisabled(String reason) {
    logInfo("005", "Test disabled because '{}'", reason);
  }

  public void executeScriptWithScriptEngine(String scriptPath, String engineName) {
    logInfo("006", "Execute script '{}' with script engine '{}'", scriptPath, engineName);
  }

  public SpinScriptException noScriptEngineFoundForLanguage(String scriptLanguage) {
    return new SpinScriptException(exceptionMessage("002", "No script engine found for script language '{}'", scriptLanguage));
  }

  public SpinScriptException scriptExecutionError(String scriptPath, Throwable cause) {
    return new SpinScriptException(exceptionMessage("004", "Error during execution of script '{}'", scriptPath), cause);
  }

  public SpinScriptException cannotCastVariableError(String name, Throwable e) {
    return new SpinScriptException(exceptionMessage("005", "Cannot cast variable '{}', wrong type", name), e);
  }

  public SpinScriptException unableToUnwrapRhinoJsVariable(String name, Throwable e) {
    return new SpinScriptException(exceptionMessage("006", "Unable to unwrap rhinojs variable with name '{}'", name), e);
  }

  public SpinScriptException unableToFindUnwrapMethod(Throwable e) {
    return new SpinScriptException(exceptionMessage("007", "Unable to find unwrap method of NativeJavaObject"));
  }

  public SpinRuntimeException unableToUnwrapRhinoJsWrappedException(String message) {
    return new SpinScriptException(exceptionMessage("008", "Unable to unwrap rhinojs wrapped exception with message '{}'", message));
  }

}
