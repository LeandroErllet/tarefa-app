function focusFix(input) {
    input.focus();
    var tmpStr = input.val();
    input.val('');
    input.val(tmpStr);

    function cancelEditListener() {
        cancelEdit();
        $(this).unbind('keydown', cancelEditListener);
        return false;
    }
    input.bind('keydown', 'esc', cancelEditListener);
}

function bindResetInput() {
    var input = $('#tarefaForm\\:tarefaInput');

    function clearAndRebind() {
        clearTarefa();

        setTimeout(bindResetInput, 100);
        return false;
    }

    input.unbind('keydown', clearAndRebind);
    input.bind('keydown', 'esc', clearAndRebind);
}