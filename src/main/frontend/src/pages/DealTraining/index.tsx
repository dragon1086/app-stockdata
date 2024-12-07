{/* previous imports remain the same */}

/* ... previous code remains the same until the useForm call ... */

  const { register, handleSubmit, watch, setValue, formState: { errors } } = useForm<DealTrainingForm>({
    resolver: yupResolver<DealTrainingForm>(validationSchema),
    context: {
      isCompanyNameEnabled,
      isStartDateEnabled,
      isValuationEnabled,
    },
    defaultValues: {
      companyName: '',
      slotAmount: 0,
      portion: 0,
      startDate: '',
      valuationPercent: 0,
      level: 'beginner'
    }
  });

/* ... rest of the code remains the same ... */