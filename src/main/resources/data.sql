INSERT StageType (Id, Name, StatusId, IsDeleted) VALUES (0, N'All Stage Types', 2, 0);
INSERT StageType (Id, Name, StatusId, IsDeleted) VALUES (1, N'Recette', 2, 0);
INSERT StageType (Id, Name, StatusId, IsDeleted) VALUES (2, N'Pre-Prod Amazon', 2, 0);
INSERT StageType (Id, Name, StatusId, IsDeleted) VALUES (3, N'Prod Amazon', 2, 0);
INSERT StageType (Id, Name, StatusId, IsDeleted) VALUES (1002, N'Dev', 2, 0);


INSERT ReleasePath (Id, Name, Description, StatusId, CreatedOn, CreatedById, ModifiedOn, ModifiedById, IsDeleted) VALUES (1, N'TestMehdi', N'Test...Mehdi', 2, NOW(), 10006, NOW(), 10033, 0);
INSERT ReleasePath (Id, Name, Description, StatusId, CreatedOn, CreatedById, ModifiedOn, ModifiedById, IsDeleted) VALUES (2, N'Test Recette fake Path 1', N'déploiement fake sur ovh par http', 2, '2007-05-06', 9005, NOW(), 10033, 0);
INSERT ReleasePath (Id, Name, Description, StatusId, CreatedOn, CreatedById, ModifiedOn, ModifiedById, IsDeleted) VALUES (3, N'Recette, PP, Prod Plugwine', N'QA on OVH for plugwine', 2, '2007-05-06', 10006, NOW(), 10033, 0);
INSERT ReleasePath (Id, Name, Description, StatusId, CreatedOn, CreatedById, ModifiedOn, ModifiedById, IsDeleted) VALUES (4, N'CI DEV Plugwine', N'Continuous Integration Plugwine', 2, '2008-05-06', 10033, NOW(), 10033, 0);
INSERT ReleasePath (Id, Name, Description, StatusId, CreatedOn, CreatedById, ModifiedOn, ModifiedById, IsDeleted) VALUES (5, N'Test2', N'', 2, '2007-07-06', 10033, NOW(), 10033, 0);
INSERT ReleasePath (Id, Name, Description, StatusId, CreatedOn, CreatedById, ModifiedOn, ModifiedById, IsDeleted) VALUES (6, N'PP, Prod', N'Pre Prod to Prod mostly used for hot fixes or deployment from branches', 2, NOW(), 10033, NOW()), 10033, 0);


INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (1, 1, 1002, 1, 1, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (2, 2, 1, 1, 1, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (3, 3, 1, 1, 1, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (4, 1, 1, 2, 2, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (5, 3, 2, 2, 2, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (6, 4, 1002, 1002, 1, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (7, 5, 1002, 1002, 1, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (8, 6, 2, 2, 1, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (9, 6, 1003, 3, 2, 0);
INSERT Stage (Id, ReleasePathId, EnvironmentId, StageTypeId, Rank, IsDeleted) VALUES (10, 3, 1003, 3, 3, 0);



INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (1, N'Recette Test Template', 2, 1, 1, 1, N'ProjetPlugWine', N'PlugWine_HopperRM', N'', N'', 0, '2011-09-02', 10006, NOW(), 10006, NOW(), 0, 10006, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (2, N'R7_Hopper', 2, 3, 1, 1, N'ProjetPlugWine', N'HopperR7', N'', N'', 1, '2011-09-02', 10006, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (3, N'Recette Fake', 2, 2, 1, 1, N'ProjetPlugWine', N'PlugWine_Fake', N'', N'', 1, '2011-09-02', 9005, NOW(), 9002, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (4, N'Backup_CI_Hopper_SrvRecette2', 2, 1, 1, 1, N'ProjetPlugWine', N'HopperRM', N'', N'', 0, '2011-09-02', 10006, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (5, N'New Release Template 1_D_140403.11:00:59', 2, 1, 0, 0, N'', N'', N'', N'', 0, '2011-09-02', 10006, NOW(), 10006, NOW(), 0, 10006, N'', 1);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (6, N'Backup_CI_WinID_SrvRecette2', 2, 1, 1, 1, N'ProjetPlugWine', N'WinIdRM', N'', N'', 1, '2011-09-02', 10006, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (7, N'Monet_Recette_on_SRVRECETTE2_D_140422.17:01:12', 2, 1, 1, 1, N'ProjetPlugWine', N'PlugWine_MonetRM', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 1);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (8, N'Backup_CI_Monet_SrvRecette2', 2, 1, 1, 1, N'ProjetPlugWine', N'MonetRM', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (9, N'HopperBad', 2, 1, 1, 1, N'ProjetPlugWine', N'PlugWineHopperRMbad', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (10, N'CI_Hopper', 2, 4, 1, 1, N'ProjetPlugWine', N'HopperCI', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (11, N'CI_WinId', 2, 4, 1, 1, N'ProjetPlugWine', N'WinIdCI', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (12, N'R7_WinId', 2, 3, 1, 1, N'ProjetPlugWine', N'WinIdR7', N'', N'', 1, '2011-09-02', 10033, NOW(), 10012, NOW(), 0, 10012, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (13, N'CI_Monet', 2, 4, 1, 1, N'ProjetPlugWine', N'MonetCI', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (14, N'R7_Monet', 2, 3, 1, 1, N'ProjetPlugWine', N'MonetR7', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (15, N'CI_MsMq', 2, 4, 1, 1, N'ProjetPlugWine', N'msmqCI', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (16, N'R7_MsMq', 2, 3, 1, 1, N'ProjetPlugWine', N'msmqR7', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (17, N'Test2', 2, 5, 0, 0, N'', N'', N'', N'', 0, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (18, N'TestRemote', 2, 3, 0, 0, N'', N'', N'', N'', 0, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (19, N'Dummy', 2, 4, 1, 1, N'ProjetPlugWine', N'HopperCI', N'', N'', 0, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (20, N'PP_Hopper', 2, 6, 1, 1, N'ProjetPlugWine', N'HopperPP', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (21, N'PP_WinId_D_140602.13:36:26', 2, 3, 1, 1, N'ProjetPlugWine', N'WinIdPP', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 1);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (22, N'PP_WinId', 2, 6, 1, 1, N'ProjetPlugWine', N'WinIdPP', N'', N'', 1, '2011-09-02', 10033, NOW(), 10012, NOW(), 0, 10012, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (23, N'PP_Monet', 2, 6, 1, 1, N'ProjetPlugWine', N'MonetPP', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);
INSERT ApplicationVersion (Id, Name, StatusId, ReleasePathId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, Notes, Description, CanTriggerReleaseFromBuild, CreatedOn, CreatedById, ModifiedOn, ModifiedById, LockedOn, LockedById, UnLockedById, LockedSessionId, IsDeleted) VALUES (24, N'PP_MsMq', 2, 6, 1, 1, N'ProjetPlugWine', N'msmqPP', N'', N'', 1, '2011-09-02', 10033, NOW(), 10033, NOW(), 0, 10033, N'', 0);

INSERT Component (Id, Name, StatusId, PackageLocation, FileExtensionFilter, DeployerToolId, Command, Arguments, Timeout, VariableReplacementModeId, Description, TypeId, TeamFoundationServerId, TeamProjectCollectionId, TeamProject, BuildDefinition, ActionTypeId, CreatedOn, CreatedById, ModifiedOn, ModifiedById, IsDeleted, IsPublishedByMicrosoft) VALUES (3951, N'Modify Key', 2, N'', N'', 2006, N'IniUtility.exe', N'-ModifyKeyValue -INIFile "__INI File Name__" -SectionName "__Section Name__" -KeyName "__Old Key Name__" -NewKeyName "__New Key Name__" -CreateFileIfNotExists', 5, 1, N'Modify a key in a specific section', 4, 0, 0, N'', N'', 2, NOW(), 0, NOW(), 0, 0, 1);

INSERT ConfigurationVariable (Id, Name, ComponentId, TypeId, Description, IsSystem, IsDeleted, IsParameter) VALUES (10507, N'INI File Name', 3951, 1, N'The name and path to the INI File', 0, 0, 1);
INSERT ConfigurationVariable (Id, Name, ComponentId, TypeId, Description, IsSystem, IsDeleted, IsParameter) VALUES (10508, N'Section Name', 3951, 1, N'The section name where the key will be modified', 0, 0, 1);
INSERT ConfigurationVariable (Id, Name, ComponentId, TypeId, Description, IsSystem, IsDeleted, IsParameter) VALUES (10509, N'ABC Key Name', 3951, 1, N'The old key name to be changed', 0, 0, 1);
INSERT ConfigurationVariable (Id, Name, ComponentId, TypeId, Description, IsSystem, IsDeleted, IsParameter) VALUES (10510, N'New Key Name', 3951, 1, N'The new key name (leave empty if the key is not changed)', 0, 0, 1);

INSERT ApplicationVersionStage (Id, ApplicationVersionId, StageId, Workflow) VALUES (2504, 11, 6, N'<DeploymentSequenceActivity>');
INSERT ApplicationVersionStageActivity (Id, ComponentId, ApplicationVersionStageId, ServerId, WorkflowActivityId) VALUES (16912, 3951, 2504, 1005, N'a5e4124d-7b05-42d3-9770-b97b4d6b4738');

INSERT ConfigurationVariableValue (Id, ApplicationVersionStageActivityId, ConfigurationVariableId, ServerId, Value) VALUES (75974, 16912, 10507, "s1", N'\\SRV-SQL\BackupDB-RM\PlugWineV1.bak');
INSERT ConfigurationVariableValue (Id, ApplicationVersionStageActivityId, ConfigurationVariableId, ServerId, Value) VALUES (75975, 16912, 10508, "s1", N'\\VAL1');
INSERT ConfigurationVariableValue (Id, ApplicationVersionStageActivityId, ConfigurationVariableId, ServerId, Value) VALUES (75976, 16912, 10509, "s1", N'\\VAL2');
INSERT ConfigurationVariableValue (Id, ApplicationVersionStageActivityId, ConfigurationVariableId, ServerId, Value) VALUES (75977, 16912, 10510, "s1", N'\\VAL3');


