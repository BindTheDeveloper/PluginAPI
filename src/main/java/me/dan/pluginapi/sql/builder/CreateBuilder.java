package me.dan.pluginapi.sql.builder;

import lombok.Getter;
import me.dan.pluginapi.sql.SQLColumn;

import java.util.ArrayList;
import java.util.List;

public class CreateBuilder {

    private final String id;
    private final List<SQLColumn> columnList;

    @Getter
    private SQLColumn primary;

    public CreateBuilder(String id) {
        this.id = id;
        this.columnList = new ArrayList<>();
    }

    public CreateBuilder addColumn(SQLColumn sqlColumn) {
        if (primary == null) {
            setPrimary(sqlColumn);
        }
        this.columnList.add(sqlColumn);
        return this;
    }

    public CreateBuilder setPrimary(SQLColumn sqlColumn) {
        this.primary = sqlColumn;
        return this;
    }

    public String build() {
        int i = 1;
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS ").append(id).append(" (");
        for (SQLColumn column : columnList) {
            query.append(column.getId());
            query.append(" ");
            query.append(column.getType().getType());
            if (i != columnList.size()) {
                query.append(", ");
            }
            i++;
        }

        query.append(", PRIMARY KEY (").append(primary.getId()).append(")");
        query.append(");");
        return query.toString();
    }

}
