package com.example.totalexpenseapi.service;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.datastax.spark.connector.japi.SparkContextJavaFunctions;
import com.example.totalexpenseapi.entity.Expense;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final SparkContext sparkContext;

    public List<Expense> getAllExpenses() {
        SparkContextJavaFunctions functions = CassandraJavaUtil.javaFunctions(sparkContext);
        JavaRDD<Expense> myDataJavaRDD = functions.cassandraTable("bigdatadbcassandra",
                "user_data", CassandraJavaUtil.mapRowTo(Expense.class));
        return myDataJavaRDD.collect();
    }

    public Float getExpenseByUsername(Long userId) {
        SparkContextJavaFunctions functions = CassandraJavaUtil.javaFunctions(sparkContext);
        JavaRDD<Expense> myDataJavaRDD = functions.cassandraTable("bigdatadbcassandra",
                "user_data", CassandraJavaUtil.mapRowTo(Expense.class));

        Float result = myDataJavaRDD.filter(expense -> Objects.equals(expense.getUserid(), userId))
                .map(expense -> expense.getPayment() * expense.getCount())
                .reduce(Float::sum);

        return (float) ((int) (result * 100)) / 100;
    }
}
