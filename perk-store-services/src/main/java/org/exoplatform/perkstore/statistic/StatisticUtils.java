package org.exoplatform.perkstore.statistic;

import java.util.*;
import java.util.Map.Entry;

import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class StatisticUtils {

  private static final Log STATISTIC_LOG = ExoLogger.getLogger("PerkStoreStatistics");

  private StatisticUtils() {
  }

  public static final String LOCAL_SERVICE  = "local_service";

  public static final String REMOTE_SERVICE = "remote_service";

  public static final String OPERATION      = "operation";

  public static final String STATUS         = "status";

  public static final String STATUS_CODE    = "status_code";

  public static final String DURATION       = "duration_ms";

  public static final String ERROR_MSG      = "error_msg";

  public static final String PARAMETERS     = "parameters";

  public static void addStatisticEntry(Map<String, Object> parameters) {
    if (parameters == null || parameters.isEmpty()) {
      throw new IllegalArgumentException("parameters is mandatory");
    }

    parameters = new HashMap<>(parameters);
    StringBuilder logEntry = new StringBuilder();

    if (parameters.containsKey(LOCAL_SERVICE)) {
      logEntry.append(LOCAL_SERVICE).append("=").append(parameters.get(LOCAL_SERVICE)).append(" ");
      parameters.remove(LOCAL_SERVICE);
    } else if (parameters.containsKey(REMOTE_SERVICE)) {
      logEntry.append(REMOTE_SERVICE).append("=").append(parameters.get(REMOTE_SERVICE)).append(" ");
      parameters.remove(REMOTE_SERVICE);
    } else {
      throw new IllegalStateException("neither 'local_service' nor 'remote_service' exists in parameters");
    }

    if (parameters.containsKey(OPERATION)) {
      logEntry.append(OPERATION).append("=").append(parameters.get(OPERATION)).append(" ");
      parameters.remove(OPERATION);
    } else {
      throw new IllegalStateException("'OPERATION' doesn't exists in parameters");
    }

    if (parameters.containsKey(STATUS)) {
      logEntry.append(STATUS).append("=").append(parameters.get(STATUS)).append(" ");
      parameters.remove(STATUS);
    }

    if (parameters.containsKey(STATUS_CODE)) {
      logEntry.append(STATUS_CODE).append("=").append(parameters.get(STATUS_CODE)).append(" ");
      parameters.remove(STATUS_CODE);
    }

    if (parameters.containsKey(DURATION)) {
      logEntry.append(DURATION).append("=").append(parameters.get(DURATION)).append(" ");
      parameters.remove(DURATION);
    }

    if (parameters.containsKey(ERROR_MSG)) {
      logEntry.append(ERROR_MSG).append("=").append(parameters.get(ERROR_MSG)).append(" ");
      parameters.remove(ERROR_MSG);
    }

    logEntry.append(PARAMETERS).append("=").append("\"");
    Set<Entry<String, Object>> parametersEntries = parameters.entrySet();
    Iterator<Entry<String, Object>> parametersIterator = parametersEntries.iterator();
    while (parametersIterator.hasNext()) {
      Map.Entry<String, Object> entry = parametersIterator.next();

      logEntry.append(entry.getKey()).append(":").append(entry.getValue());

      parametersIterator.remove();
      if (!parameters.isEmpty()) {
        logEntry.append(",");
      }
    }
    logEntry.append("\"");

    STATISTIC_LOG.info(logEntry.toString());
  }
}
