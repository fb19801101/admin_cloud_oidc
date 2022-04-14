package com.provider_oidc_login.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.provider_oidc_login.domain.auth.AuthRole;
import lombok.*;

/**
 * 
 * 
 * @author chenshun
 * @email 
 * @date 2021-03-13 10:29:39
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class AuthEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限id
	 */
	private Integer id;
	/**
	 * 操作人机构根节点
	 */
	private String providerId;
	/**
	 * 操作人id
	 */
	private Integer operatorId;

	/**
	 * 授权对象机构根节点
	 */
	private String objProvider;
	/**
	 * 授权对象id，与objType有关0为人员，1为岗位
	 */
	private Integer objId;
	/**
	 * 授权对象类型，0人员，1岗位
	 */
	private Integer objType;
	/**
	 * 授权对象组织机构节点
	 */
	private Integer objNode;
	/**
	 * 授权对象名称
	 */
	private String objName;
	/**
	 * 授权对象岗位id
	 */
	private Integer objPostId;
	/**
	 * 授权对象岗位名称
	 */
	private String objPostName;
	/**
	 * 授权对象岗位Code
	 */
	private String objPostCode;
	/**
	 * 授权对象路径
	 */
	private String objPath;
	/**
	 * 授权组织机构节点索引
	 */
	private Integer objIndex;

	/**
	 * 授权组织机构根节点
	 */
	private String nodeProvider;
	/**
	 * 授权组织机构节点id
	 */
	private Integer nodeId;
	/**
	 * 授权组织机构节点code
	 */
	private String nodeCode;
	/**
	 * 授权组织机构节点类型
	 */
	private Integer nodeType;
	/**
	 * 授权组织机构节点路径
	 */
	private String nodePath;
	/**
	 * 授权组织机构节点索引
	 */
	private Integer nodeIndex;

	/**
	 * 授权组织机构根上级节点
	 */
	private String nodeParentProvider;
	/**
	 * 授权组织机构上级节点id
	 */
	private Integer nodeParentId;
	/**
	 * 授权组织机构上级节点code
	 */
	private String nodeParentCode;
	/**
	 * 授权组织机构上级节点类型
	 */
	private Integer nodeParentType;
	/**
	 * 授权组织机构上级节点路径
	 */
	private String nodeParentPath;
	/**
	 * 授权组织机构上级节点索引
	 */
	private Integer nodeParentIndex;


	/**
	 * 授权对象权限类别 VIEW MODIFY NONE
	 */
	private AuthRole authRole;
	/**
	 * 授权对象权限删除标识
	 */
	private Boolean authFlag;
	/**
	 * 组织人员浏览权限，0无，1浏览，2管理
	 */
	private Integer authOrg;
	/**
	 * 授权管理权限
	 */
	private Integer authOpAuth;
	/**
	 * 授权查询权限
	 */
	private Integer authQueryAuth;
	/**
	 * 系统日志权限
	 */
	private Integer authLog;
	/**
	 * 岗位变动查询
	 */
	private Integer authPostChange;
	/**
	 * 岗位删除查询
	 */
	private Integer authPostDelete;

	/**
	 * 授权对象创建时间
	 */
	private LocalDateTime createTime;

	public AuthEntity() {

	}

	public AuthEntity(Integer id, String providerId, Integer operatorId, String objProvider, Integer objId, Integer objType, Integer objNode, String objName, Integer objPostId, String objPostName, String objPostCode, String objPath, Integer objIndex, String nodeProvider, Integer nodeId, String nodeCode, Integer nodeType, String nodePath, Integer nodeIndex, String nodeParentProvider, Integer nodeParentId, String nodeParentCode, Integer nodeParentType, String nodeParentPath, Integer nodeParentIndex) {
		this.id = id;
		this.providerId = providerId;
		this.operatorId = operatorId;
		this.objProvider = objProvider;
		this.objId = objId;
		this.objType = objType;
		this.objNode = objNode;
		this.objName = objName;
		this.objPostId = objPostId;
		this.objPostName = objPostName;
		this.objPostCode = objPostCode;
		this.objPath = objPath;
		this.objIndex = objIndex;
		this.nodeProvider = nodeProvider;
		this.nodeId = nodeId;
		this.nodeCode = nodeCode;
		this.nodeType = nodeType;
		this.nodePath = nodePath;
		this.nodeIndex = nodeIndex;
		this.nodeParentProvider = nodeParentProvider;
		this.nodeParentId = nodeParentId;
		this.nodeParentCode = nodeParentCode;
		this.nodeParentType = nodeParentType;
		this.nodeParentPath = nodeParentPath;
		this.nodeParentIndex = nodeParentIndex;
		this.authRole = AuthRole.NONE;
		this.authFlag = true;
		this.authOrg = 0;
		this.authOpAuth = 0;
		this.authQueryAuth = 0;
		this.authLog = 0;
		this.authPostChange = 0;
		this.authPostDelete = 0;
		this.createTime = LocalDateTime.now();
	}

	public Boolean compareTo(AuthEntity authEntity) {
		if(authEntity != null) {
			if(this.objProvider.compareTo(authEntity.getObjProvider()) == 0) {
				if(this.objId.compareTo(authEntity.getObjId()) == 0) {
					if(this.nodeProvider.compareTo(authEntity.getNodeProvider()) == 0) {
						return this.nodeId.compareTo(authEntity.getNodeId()) == 0;
					}
				}
			}
		}

		return false;
	}

}
