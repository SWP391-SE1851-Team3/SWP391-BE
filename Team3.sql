

USE TEAM_3
GO

CREATE TABLE Role (
  RoleID INT IDENTITY(1,1) PRIMARY KEY,
  RoleName VARCHAR(100)
);

CREATE TABLE Users (
  UserID INT IDENTITY(1,1) PRIMARY KEY,
  UserName VARCHAR(50),
  Password VARCHAR(100),
  FullName VARCHAR(100),
  Phone VARCHAR(15),
  Email VARCHAR(100),
  RoleID INT FOREIGN KEY REFERENCES Role(RoleID),
  CreateDate DATETIME,
  EditedBy INT,
  EditDate DATETIME,
  IsActive BIT
);

CREATE TABLE Parent (
  ParentID INT IDENTITY(1,1) PRIMARY KEY,
  UserID INT UNIQUE FOREIGN KEY REFERENCES Users(UserID),
  Occupation VARCHAR(100),
  Relationship VARCHAR(100)
);

CREATE TABLE Student (
  StudentID INT IDENTITY(1,1) PRIMARY KEY,
  UserID INT UNIQUE FOREIGN KEY REFERENCES Users(UserID),
  Certification VARCHAR(100),
  Specialisation DATETIME,
  Gender BIT,
  ClassName VARCHAR(100),
  IsActive BIT
);

CREATE TABLE SchoolNurse (
  NurseID INT IDENTITY(1,1) PRIMARY KEY,
  UserID INT UNIQUE FOREIGN KEY REFERENCES Users(UserID),
  Certification VARCHAR(100),
  Specialisation VARCHAR(50)
);

CREATE TABLE SupplyCategory (
  CategoryID INT IDENTITY(1,1) PRIMARY KEY,
  CategoryName VARCHAR(100),
  RequiresColdStorage VARCHAR(100)
);

CREATE TABLE MedicalSupply (
  MedicalSupplyID INT IDENTITY(1,1) PRIMARY KEY,
  CategoryID INT FOREIGN KEY REFERENCES SupplyCategory(CategoryID),
  SupplyName VARCHAR(100),
  Unit VARCHAR(20),
  QuantityAvailable INT DEFAULT 0,
  ReorderLevel INT CHECK (ReorderLevel >= 0),
  ExpiryDate DATE,
  StorageTemperature VARCHAR(50),
  IsOutOfStock BIT DEFAULT 0
);

CREATE TABLE MedicationType (
  MedicationTypeID INT IDENTITY(1,1) PRIMARY KEY,
  Name VARCHAR(100),
  RequiresPrescription VARCHAR(100),
  UsageMethod VARCHAR(100),
  Unit VARCHAR(100)
);

CREATE TABLE MedicationSubmission (
  MedicationSubmissionID INT IDENTITY(1,1) PRIMARY KEY,
  StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
  ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
  SumissionDate DATETIME,
  Dosage VARCHAR(100),
  FrequencyPerDay VARCHAR(100),
  DurationDays VARCHAR(100),
  IsDoctorNoteIncluded BIT,
  RequiresSupervision BIT,
  StartDate DATETIME,
  EndDate DATETIME,
  Note VARCHAR(100),
  MedicationTypeID INT FOREIGN KEY REFERENCES MedicationType(MedicationTypeID)
);

CREATE TABLE MedicalEventType (
  EventTypeID INT IDENTITY(1,1) PRIMARY KEY,
  TypeName VARCHAR(100)
);

CREATE TABLE MedicalEvent (
  EventID INT IDENTITY(1,1) PRIMARY KEY,
  TypeName VARCHAR(100),
  EventLocation VARCHAR(100),
  UsageMethod VARCHAR(100),
  IsEmergency BIT,
  HasParentBeenInformed BIT,
  Temperature VARCHAR(100),
  HeartRate VARCHAR(100),
  EventDateTime DATETIME,
  StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
  NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
  EventTypeID INT FOREIGN KEY REFERENCES MedicalEventType(EventTypeID)
);

-- 1. Tạo bảng HealthCheck trước
CREATE TABLE HealthCheck (
  checkID INT IDENTITY(1,1) PRIMARY KEY,
  CheckDate DATETIME,
  Height FLOAT,
  Weight FLOAT,
  VisionLeft VARCHAR(100),
  VisionRight VARCHAR(100),
  Hearing VARCHAR(100),
  DentalCheck VARCHAR(100),
  BMI FLOAT,
  overallResult VARCHAR(100),
  Temperature VARCHAR(100),
  NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
  StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
  FormID INT UNIQUE -- Tạm thời chưa đặt FOREIGN KEY ở đây, vì bảng HealthConsentForm chưa tồn tại
);

-- 2. Tạo bảng HealthCheck_Schedule
CREATE TABLE HealthCheck_Schedule (
  Health_ScheduleID INT IDENTITY(1,1) PRIMARY KEY,
  Schedule_Date DATETIME,
  Name VARCHAR(100),
  Location VARCHAR(100),
  Notes VARCHAR(100),
  Status BIT,
  CheckID INT FOREIGN KEY REFERENCES HealthCheck(checkID)
);

-- 3. Tạo bảng HealthConsentForm
CREATE TABLE HealthConsentForm (
  FormID INT IDENTITY(1,1) PRIMARY KEY,
  ConsentDate DATETIME,
  ConsentLocation VARCHAR(100),
  IsAgreed BIT,
  Notes VARCHAR(100),
  ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
  StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
  Health_ScheduleID INT FOREIGN KEY REFERENCES HealthCheck_Schedule(Health_ScheduleID)
);

-- 4. Cập nhật lại khóa ngoại trong bảng HealthCheck (sau khi đã có HealthConsentForm)
ALTER TABLE HealthCheck
ADD CONSTRAINT FK_HealthCheck_Form
FOREIGN KEY (FormID) REFERENCES HealthConsentForm(FormID);

-- 5. Tạo bảng HealthConsultation
CREATE TABLE HealthConsultation (
  ConsultID INT IDENTITY(1,1) PRIMARY KEY,
  ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
  StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
  NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
  Reason VARCHAR(100),
  Status BIT,
  CheckID INT FOREIGN KEY REFERENCES HealthCheck(checkID)
);

CREATE TABLE Vaccines (
  Vaccine_id INT IDENTITY(1,1) PRIMARY KEY,
  Name VARCHAR(100),
  Manufacturer VARCHAR(100),
  description VARCHAR(100),
  recommended_ages VARCHAR(100),
  doses_required INT,
  created_at VARCHAR(100),
  updated_at VARCHAR(100)
);

CREATE TABLE vaccine_batches (
  batch_id INT IDENTITY(1,1) PRIMARY KEY,
  Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
  batch_number VARCHAR(100),
  expiration_date DATETIME,
  quantity_received VARCHAR(100),
  quantity_remaining VARCHAR(100),
  received_date DATETIME,
  created_at VARCHAR(100),
  updated_at VARCHAR(100)
);

CREATE TABLE vaccination_schedule (
  schedule_id INT IDENTITY(1,1) PRIMARY KEY,
  Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
  batch_number VARCHAR(100),
  scheduled_date DATETIME,
  location VARCHAR(100),
  NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
  received_date DATETIME,
  status VARCHAR(100),
  notes VARCHAR(100)
);

CREATE TABLE vaccination_records (
  VaccinationRecordID INT IDENTITY(1,1) PRIMARY KEY,
  Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
  StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
  schedule_id INT FOREIGN KEY REFERENCES vaccination_schedule(schedule_id),
  dose_number VARCHAR(100),
  RequirementParentConsent BIT,
  notes VARCHAR(100),
  batch_id INT FOREIGN KEY REFERENCES vaccine_batches(batch_id)
);

CREATE TABLE consent_forms (
  consent_id INT IDENTITY(1,1) PRIMARY KEY,
  StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
  ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
  schedule_id INT FOREIGN KEY REFERENCES vaccination_schedule(schedule_id),
  Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
  RequirementParentConsent BIT,
  consent_date DATETIME,
  consent_status BIT,
  Reason VARCHAR(100),
  HasAllergy BIT,
  UNIQUE (StudentID, ParentID, Vaccine_id)
);

CREATE TABLE post_vaccination_observations (
  observation_id INT IDENTITY(1,1) PRIMARY KEY,
  VaccinationRecordID INT UNIQUE FOREIGN KEY REFERENCES vaccination_records(VaccinationRecordID),
  observation_time DATETIME,
  symptoms NVARCHAR(MAX),
  severity NVARCHAR(MAX),
  notes VARCHAR(255),
  NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID)
);
