package com.shl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;

/**
 * This class is used for configuring couchbase with springboot
 * @author sshail
 */
@Configuration
@EnableAutoConfiguration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration  {
	@Value("${couchbase.cluster.bucket}")
	private String bucketName;

	@Value("${couchbase.cluster.password}")
	private String password;

	@Value("${couchbase.cluster.ip}")
	private String ip;

	@Bean
	public Bucket bucket(){
		return CouchbaseCluster.create(ip).openBucket(bucketName);
	}
	
	@Override
	protected List<String> bootstrapHosts() {
		return Arrays.asList(this.ip);
	}

	@Override
	protected String getBucketName() {
		return this.bucketName;
	}

	@Override
	protected String getBucketPassword() {
		return this.password;
	}
   
}
