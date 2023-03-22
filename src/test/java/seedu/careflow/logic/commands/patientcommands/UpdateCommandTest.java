package seedu.careflow.logic.commands.patientcommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.careflow.logic.commands.patientcommands.CommandTestUtil.*;
import static seedu.careflow.testutil.TypicalDrugs.getTypicalDrugInventory;
//import static seedu.careflow.testutil.TypicalIndexes.CARL;
import static seedu.careflow.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.careflow.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.careflow.testutil.TypicalPatients.*;

import seedu.careflow.commons.core.Messages;
import seedu.careflow.logic.commands.patientcommands.UpdateCommand.EditPatientDescriptor;

import org.junit.jupiter.api.Test;
import seedu.careflow.commons.core.index.Index;
import seedu.careflow.model.*;
import seedu.careflow.model.patient.Name;
import seedu.careflow.model.patient.Patient;
import seedu.careflow.testutil.EditPatientDescriptorBuilder;
import seedu.careflow.testutil.PatientBuilder;

import java.io.File;


/**
 * Contains integration tests (interaction with the CareFlowModel) and unit tests for UpdateCommand.
 * All tests assume that the typical patient record has patinet list starting with model.getFilteredPatientList().get(0).
 */
public class UpdateCommandTest {
    private CareFlowModel model = new CareFlowModelManager(getTypicalPatientRecord(),
            getTypicalDrugInventory(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Patient editedPatient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(editedPatient).build();
        UpdateCommand editCommand = new UpdateCommand(model.getFilteredPatientList().get(0).getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PATIENT_SUCCESS, editedPatient);

        CareFlowModel expectedCareFlowModel = new CareFlowModelManager( new PatientRecord(model.getPatientRecord()),
                new DrugInventory(model.getDrugInventory()), new UserPrefs());
        expectedCareFlowModel.setPatient(model.getFilteredPatientList().get(0), editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedCareFlowModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
//        Index indexLastPatient = Index.fromOneBased(model.getFilteredPatientList().size());
        Patient firstPatient = model.getFilteredPatientList().get(0);
        PatientBuilder personInList = new PatientBuilder(firstPatient);
        Patient editedPatient = personInList.withAddress(VALID_ADDRESS_BOB).withPhone(VALID_PHONE_BOB).build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withAddress(VALID_ADDRESS_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(model.getFilteredPatientList().get(0).getName(), descriptor);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PATIENT_SUCCESS, editedPatient);

        CareFlowModel expectedCareFlowModel = new CareFlowModelManager( new PatientRecord(model.getPatientRecord()),
                new DrugInventory(model.getDrugInventory()), new UserPrefs());
        expectedCareFlowModel.setPatient(firstPatient,editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedCareFlowModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateCommand editCommand = new UpdateCommand(model.getFilteredPatientList().get(0).getName(), new EditPatientDescriptor());
        Patient editedPatient = model.getFilteredPatientList().get(0);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PATIENT_SUCCESS, editedPatient);

        CareFlowModel expectedCareFlowModel = new CareFlowModelManager( new PatientRecord(model.getPatientRecord()),
                new DrugInventory(model.getDrugInventory()), new UserPrefs());
        assertCommandSuccess(editCommand, model, expectedMessage, expectedCareFlowModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST);
        Patient personInFilteredList = model.getFilteredPatientList().get(0);
        Patient editedPatient = new PatientBuilder(personInFilteredList).withDrugAllergy(VALID_DRUG_ALLERGY_BOB).build();
        UpdateCommand editCommand = new UpdateCommand(model.getFilteredPatientList().get(0).getName(),
                new EditPatientDescriptorBuilder().withDrugAllergy(VALID_DRUG_ALLERGY_BOB).build());

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PATIENT_SUCCESS, editedPatient);

        CareFlowModel expectedCareFlowModel = new CareFlowModelManager(new PatientRecord(model.getPatientRecord()),
                new DrugInventory(model.getDrugInventory()), new UserPrefs());
        expectedCareFlowModel.setPatient(model.getFilteredPatientList().get(0),editedPatient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedCareFlowModel);
    }

    @Test
    public void execute_duplicatePatientUnfilteredList_failure() {
        Patient firstPatient = model.getFilteredPatientList().get(0);
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(firstPatient).build();
        // assume the typicalPatientRecord's patient list has more than 2 patients
        UpdateCommand editCommand = new UpdateCommand(model.getFilteredPatientList().get(1).getName(), descriptor);

        assertCommandFailure(editCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_duplicatePatientFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST);

        // edit person in filtered list into a duplicate in address book
        Patient personInList = model.getPatientRecord().getPatientList().get(1);
        UpdateCommand editCommand = new UpdateCommand(model.getPatientRecord().getPatientList().get(0).getName(),
                new EditPatientDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PATIENT);
    }

    @Test
    public void execute_invalidPatientNameUnfilteredList_failure() {
        UpdateCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        // assume"A NOT FOUND NAME" is not inside list of patient names
        UpdateCommand editCommand = new UpdateCommand(new Name("A NOT FOUND NAME"), descriptor);

        assertCommandFailure(editCommand, model, String.format(UpdateCommand.MESSAGE_PATIENT_NOT_FOUND,"A NOT FOUND NAME" ));
    }


    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(model.getFilteredPatientList().get(0).getName(),
                DESC_AMY);

        // same values -> returns true
        UpdateCommand.EditPatientDescriptor copyDescriptor = new EditPatientDescriptor(DESC_AMY);
        UpdateCommand commandWithSameValues = new UpdateCommand(model.getFilteredPatientList().get(0).getName(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(model.getFilteredPatientList().get(1).getName(), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(model.getFilteredPatientList().get(0).getName(), DESC_BOB)));
    }

}

