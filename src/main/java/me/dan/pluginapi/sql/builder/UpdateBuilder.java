package me.dan.pluginapi.sql.builder;

import me.dan.pluginapi.sql.SQLData;
import me.dan.pluginapi.sql.SQLRequirement;

public class UpdateBuilder {

    private final String table;
    private final SQLData[] sqlDataArray;
    private SQLRequirement[] sqlRequirements;

    public UpdateBuilder(String table, SQLData... sqlDataArray) {
        this.table = table;
        this.sqlDataArray = sqlDataArray;
    }

    public UpdateBuilder setRequirements(SQLRequirement... sqlRequirements) {
        this.sqlRequirements = sqlRequirements;
        return this;
    }

    public String build() {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ").append(table).append(" SET ");
        int i = 1;
        for (SQLData sqlData : sqlDataArray) {
            queryBuilder.append(sqlData.toWhere());
            if (i < sqlDataArray.length) {
                queryBuilder.append(", ");
            }
            i++;
        }

        if (sqlRequirements == null) {
            return queryBuilder.toString();
        }

        queryBuilder.append(" WHERE ");
        i = 1;
        for (SQLRequirement sqlRequirement : sqlRequirements) {
            queryBuilder.append(sqlRequirement.toWhere());
            if (i != sqlRequirements.length) {
                queryBuilder.append(" AND ");
            }
            i++;
        }
        return queryBuilder.toString();
    }

}
