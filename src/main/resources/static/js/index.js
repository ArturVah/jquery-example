let $categories = $('#categories');

$.get('/api/categories', function (categories) {
    loadCategories(categories);
});

function loadCategories(categories) {
    loadSelectOptions($categories, categories);
}

function loadSubCategories() {
    let categoryId = $categories.val();
    $.get('/api/subCategories?categoryId=' + categoryId, function (subCategories) {
        let $subCategoriesDiv = $('#subCategoriesDiv');
        $subCategoriesDiv.empty();

        subCategories.forEach(function (subCategory) {
            let label = $('<label/>').html(subCategory.name);

            let topicCheckbox = $('<input/>').attr({
                type: 'checkbox'
            }).attr('topicId', subCategory.id)
                .on('change', function () {
                    if ($(this).is(":checked")) {
                        let topicId = $(this).attr('topicId');
                        $.get('/api/questions?topicId=' + topicId, function (questions) {
                            let $questions = $('#questions');

                            questions.forEach(function (question) {
                                let label = $('<label/>')
                                    .attr('topicId', question.topicId)
                                    .html(question.text);

                                let questionCheckbox = $('<input/>').attr({
                                    type: 'checkbox'
                                })
                                    .attr('questionId', question.id)
                                    .attr('topicId', question.topicId);

                                $questions.append($('<br/>').attr('topicId', question.topicId));
                                $questions.append(label.prepend(questionCheckbox));
                            });
                        })
                    } else {
                        let $questions = $('#questions');

                        let topicId = $(this).attr('topicId');
                        let selector = $("[topicId = '" + topicId + "']");

                        let questionsToRemove = $questions.find(selector);

                        questionsToRemove.remove()
                    }
                });

            $subCategoriesDiv.append($('<br/>'));
            $subCategoriesDiv.append(label.prepend(topicCheckbox));
        })
    })
}

function createTest() {
    let $questions = $('#questions');
    let selector = $('input:checked');

    let questions = $questions.find(selector).map(function() {
        return $(this).attr('questionId');
    }).toArray();

    alert(questions);
}

function loadSelectOptions($select, data) {
    $select.children().remove();
    data.forEach(function (item) {
        let option = $('<option/>', {
            value: item.id,
            text: item.name
        });
        $select.append(option)
    });

    $select.val([]);
}