CREATE DATABASE School_Medical_Management_System

USE School_Medical_Management_System

-- Bảng Role
CREATE TABLE Role (
    RoleID INT PRIMARY KEY,
    RoleName VARCHAR(100)
);

-- Bảng Users
CREATE TABLE Users (
    UserID INT PRIMARY KEY,
    UserName VARCHAR(50),
    [Password] VARCHAR(100),
    FullName VARCHAR(100),
    Phone VARCHAR(15),
    Email VARCHAR(100),
    RoleID INT FOREIGN KEY REFERENCES Role(RoleID),
    CreateDate DATETIME,
    EditedBy INT,
    EditDate DATETIME,
    IsActive BIT
);

-- Bảng SchoolNurse
CREATE TABLE SchoolNurse (
    NurseID INT PRIMARY KEY,
    UserID INT UNIQUE FOREIGN KEY REFERENCES Users(UserID),
    Certification VARCHAR(100),
    Specialisation VARCHAR(50)
);

-- Bảng Parent
CREATE TABLE Parent (
    ParentID INT PRIMARY KEY,
    Occupation VARCHAR(100),
    Relationship VARCHAR(100)
);

-- Bảng Student
CREATE TABLE Student (
    StudentID INT PRIMARY KEY,
    Gender BIT,
    ClassName VARCHAR(100),
    IsActive BIT,
    ParentID INT UNIQUE FOREIGN KEY REFERENCES Parent(ParentID)
);

-- Bảng StudentHealthProfile
CREATE TABLE StudentHealthProfile (
    ProfileID INT PRIMARY KEY,
    StudentID INT UNIQUE FOREIGN KEY REFERENCES Student(StudentID),
    AllergyDetails TEXT,
    ChronicDiseases TEXT,
    TreatmentHistory TEXT,
    VisionLeft VARCHAR(20),
    VisionRight VARCHAR(20),
    Hearings_Score VARCHAR(100),
    LastUpdated DATETIME,
    Height FLOAT,
    Weight FLOAT,
    ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
    NoteOfParent NVARCHAR(100)
);

-- Bảng SupplyCategory
CREATE TABLE SupplyCategory (
    CategoryID INT PRIMARY KEY,
    CategoryName VARCHAR(100),
    RequiresColdStorage VARCHAR(100),
    ExpiryDate DATE
);

-- Bảng MedicalEvent
CREATE TABLE MedicalEvent (
    EventID INT PRIMARY KEY,
    UsageMethod VARCHAR(100),
    IsEmergency BIT,
    HasParentBeenInformed BIT,
    Temperature VARCHAR(100),
    HeartRate VARCHAR(100),
    EventDateTime DATETIME,
    ParentID INT FOREIGN KEY REFERENCES Parent(ParentID)
);

-- Bảng Vaccines
CREATE TABLE Vaccines (
    Vaccine_id INT PRIMARY KEY,
    Name VARCHAR(100),
    Manufacturer VARCHAR(100),
    Description VARCHAR(100),
    Recommended_ages VARCHAR(100),
    Doses_required INT,
    Created_at VARCHAR(100),
    Updated_at VARCHAR(100)
);

-- Bảng HealthCheck
CREATE TABLE HealthCheck (
    CheckID INT PRIMARY KEY,
    FormID INT UNIQUE -- sẽ tham chiếu đến HealthConsentForm sau
);

-- Bảng MedicalSupply
CREATE TABLE MedicalSupply (
    MedicalSupplyID INT PRIMARY KEY,
    CategoryID INT FOREIGN KEY REFERENCES SupplyCategory(CategoryID),
    SupplyName VARCHAR(100),
    Unit VARCHAR(20),
    QuantityAvailable INT DEFAULT 0,
    ReorderLevel INT CHECK (ReorderLevel >= 0),
    StorageTemperature VARCHAR(50),
    DateAdded DATE,
    CheckID INT FOREIGN KEY REFERENCES HealthCheck(CheckID),
    EventID INT FOREIGN KEY REFERENCES MedicalEvent(EventID),
    Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id)
);

-- Bảng MedicationSubmission
CREATE TABLE MedicationSubmission (
    MedicationSubmissionID INT PRIMARY KEY,
    StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
    ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
    SumissionDate DATETIME,
    Dosage VARCHAR(100),
    FrequencyPerDay VARCHAR(100),
    DurationDays VARCHAR(100),
    IsDoctorNoteIncluded BIT,
    Request_Supervisor BIT,
    StartDate DATETIME,
    EndDate DATETIME,
    Note VARCHAR(100)
);

-- Bảng Confirm_MedicationSubmission
CREATE TABLE Confirm_MedicationSubmission (
    ConfirmID INT PRIMARY KEY,
    MedicationSubmissionID INT UNIQUE FOREIGN KEY REFERENCES MedicationSubmission(MedicationSubmissionID),
    Status BIT,
    NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
    Evidence NVARCHAR(100),
    Receive_Medicine BIT
);

-- Bảng MedicationType
CREATE TABLE MedicationType (
    MedicationTypeID INT PRIMARY KEY,
    Name VARCHAR(100),
    RequiresPrescription VARCHAR(100),
    UsageMethod VARCHAR(100),
    Unit VARCHAR(100),
    MedicationSubmissionID INT FOREIGN KEY REFERENCES MedicationSubmission(MedicationSubmissionID)
);

-- Bảng MedicalEventType
CREATE TABLE MedicalEventType (
    EventTypeID INT PRIMARY KEY,
    TypeName VARCHAR(100)
);

-- Bảng MedicalEvent_EventType (nhiều-nhiều)
CREATE TABLE MedicalEvent_EventType (
    EventID INT FOREIGN KEY REFERENCES MedicalEvent(EventID),
    EventTypeID INT FOREIGN KEY REFERENCES MedicalEventType(EventTypeID),
    PRIMARY KEY (EventID, EventTypeID)
);

-- Bảng MedicalEvent_Nurse (nhiều-nhiều)
CREATE TABLE MedicalEvent_Nurse (
    NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
    EventID INT FOREIGN KEY REFERENCES MedicalEvent(EventID),
    PRIMARY KEY (NurseID, EventID)
);

-- Bảng MedicalEventDetails
CREATE TABLE MedicalEventDetails (
    StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
    EventID INT FOREIGN KEY REFERENCES MedicalEvent(EventID),
    Note VARCHAR(100),
    Result VARCHAR(100),
    ProcessingStatus VARCHAR(100),
    PRIMARY KEY (StudentID, EventID)
);

-- Bảng Notifications_MedicalEventDetails
CREATE TABLE Notifications_MedicalEventDetails (
    ParentID INT UNIQUE FOREIGN KEY REFERENCES Parent(ParentID),
    Title VARCHAR(50),
    Content NVARCHAR(100),
    EventID INT UNIQUE FOREIGN KEY REFERENCES MedicalEvent(EventID)
);

-- Bảng HealthCheck_Schedule
CREATE TABLE HealthCheck_Schedule (
    Health_ScheduleID INT PRIMARY KEY,
    Schedule_Date DATETIME,
    Name VARCHAR(100),
    Location VARCHAR(100),
    Notes VARCHAR(100),
    Status BIT
);

-- Bảng HealthConsentForm
CREATE TABLE HealthConsentForm (
    FormID INT PRIMARY KEY,
    ConsentDate DATETIME,
    ConsentLocation VARCHAR(100),
    IsAgreed BIT,
    Notes VARCHAR(100),
    ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
    StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
    Health_ScheduleID INT FOREIGN KEY REFERENCES HealthCheck_Schedule(Health_ScheduleID)
);

-- Bổ sung FK cho bảng HealthCheck (sau khi HealthConsentForm đã được tạo)
ALTER TABLE HealthCheck
ADD CONSTRAINT FK_HealthCheck_Form FOREIGN KEY (FormID) REFERENCES HealthConsentForm(FormID);

-- Bảng HealthCheck_Student
CREATE TABLE HealthCheck_Student (
    CheckID INT FOREIGN KEY REFERENCES HealthCheck(CheckID),
    StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
    Height FLOAT,
    Weight FLOAT,
    VisionLeft VARCHAR(100),
    VisionRight VARCHAR(100),
    Hearing VARCHAR(100),
    DentalCheck VARCHAR(100),
    BMI FLOAT,
    OverallResult VARCHAR(100),
    Temperature VARCHAR(100),
    PRIMARY KEY (CheckID, StudentID)
);

-- Bảng HealthCheck_SchoolNurse
CREATE TABLE HealthCheck_SchoolNurse (
    CheckID INT FOREIGN KEY REFERENCES HealthCheck(CheckID),
    NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
    PRIMARY KEY (CheckID, NurseID)
);

-- Bảng HealthConsultation
CREATE TABLE HealthConsultation (
    ConsultID INT PRIMARY KEY,
    StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
    NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
    Reason VARCHAR(100),
    Status BIT,
    CheckID INT FOREIGN KEY REFERENCES HealthCheck(CheckID)
);

-- Bảng HealthConsultation_Parent
CREATE TABLE HealthConsultation_Parent (
    ConsultID INT FOREIGN KEY REFERENCES HealthConsultation(ConsultID),
    ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
    PRIMARY KEY (ConsultID, ParentID)
);

-- Bảng vaccine_batches
CREATE TABLE vaccine_batches (
    batch_id INT PRIMARY KEY,
    Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
    batch_number VARCHAR(100),
    expiration_date DATETIME,
    quantity_received VARCHAR(100),
    quantity_remaining VARCHAR(100),
    received_date DATETIME,
    created_at VARCHAR(100),
    updated_at VARCHAR(100)
);

-- Bảng vaccination_schedule
CREATE TABLE vaccination_schedule (
    schedule_id INT PRIMARY KEY,
    Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
    batch_number VARCHAR(100),
    scheduled_date DATETIME,
    location VARCHAR(100),
    NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID),
    received_date DATETIME,
    status VARCHAR(100),
    notes VARCHAR(100)
);

-- Bảng vaccination_records
CREATE TABLE vaccination_records (
    VaccinationRecordID INT PRIMARY KEY,
    Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
    StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
    schedule_id INT FOREIGN KEY REFERENCES vaccination_schedule(schedule_id),
    dose_number VARCHAR(100),
    RequirementParentConsent BIT,
    notes VARCHAR(100),
    batch_id INT FOREIGN KEY REFERENCES vaccine_batches(batch_id)
);

-- Bảng consent_forms
CREATE TABLE consent_forms (
    consent_id INT PRIMARY KEY,
    StudentID INT FOREIGN KEY REFERENCES Student(StudentID),
    ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
    schedule_id INT FOREIGN KEY REFERENCES vaccination_schedule(schedule_id),
    Vaccine_id INT FOREIGN KEY REFERENCES Vaccines(Vaccine_id),
    RequirementParentConsent BIT,
    consent_date DATETIME,
    IsAgree BIT,
    Reason VARCHAR(100),
    HasAllergy BIT
);

-- Bảng post_vaccination_observations
CREATE TABLE post_vaccination_observations (
    observation_id INT PRIMARY KEY,
    VaccinationRecordID INT FOREIGN KEY REFERENCES vaccination_records(VaccinationRecordID),
    observation_time DATETIME,
    symptoms NVARCHAR(200),
    severity NVARCHAR(100),
    notes VARCHAR(200),
    NurseID INT FOREIGN KEY REFERENCES SchoolNurse(NurseID)
);

-- Bảng Blog_Experience
CREATE TABLE Blog_Experience (
    title VARCHAR(100),
    content VARCHAR(1000),
    create_at DATETIME
);

-- Bảng Notifications_Parent
CREATE TABLE Notifications_Parent (
    ParentID INT FOREIGN KEY REFERENCES Parent(ParentID),
    title VARCHAR(100),
    content VARCHAR(500),
    status BIT,
    create_at DATETIME
);
